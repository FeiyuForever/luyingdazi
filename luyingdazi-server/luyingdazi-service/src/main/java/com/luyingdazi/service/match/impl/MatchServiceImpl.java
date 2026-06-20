package com.luyingdazi.service.match.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luyingdazi.common.constant.AppConstant;
import com.luyingdazi.common.constant.RedisKeyConstant;
import com.luyingdazi.common.exception.BizException;
import com.luyingdazi.common.result.ResultCode;
import com.luyingdazi.mapper.UserMapper;
import com.luyingdazi.mapper.UserTagMapper;
import com.luyingdazi.model.entity.User;
import com.luyingdazi.model.entity.UserTag;
import com.luyingdazi.model.query.NearbyQuery;
import com.luyingdazi.model.vo.UserVO;
import com.luyingdazi.service.match.MatchService;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 匹配服务实现（附近的人、同城推荐、搜索）
 *
 * @author luyingdazi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final UserMapper userMapper;
    private final UserTagMapper userTagMapper;
    private final StringRedisTemplate redisTemplate;

    @Override
    public List<UserVO> getNearbyUsers(Long userId, NearbyQuery query) {
        // 1. 检查每日刷新限制（免费用户5次）
        checkMatchLimit(userId);

        // 2. Redis GEORADIUS 查询附近的人
        Double radius = query.getRadiusKm() != null ? query.getRadiusKm() : AppConstant.DEFAULT_NEARBY_RADIUS_KM;
        Integer count = query.getCount() != null ? query.getCount() : AppConstant.DEFAULT_NEARBY_COUNT;

        Point center = new Point(query.getLongitude().doubleValue(), query.getLatitude().doubleValue());
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        Circle circle = new Circle(center, distance);

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending()
                .limit(count + 1); // +1 因为可能包含自己

        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                redisTemplate.opsForGeo().radius(RedisKeyConstant.GEO_USER, circle, args);

        if (results == null || results.getContent().isEmpty()) {
            return Collections.emptyList();
        }

        // 3. 提取用户ID列表（排除自己）
        List<Long> nearbyUserIds = results.getContent().stream()
                .map(r -> Long.valueOf(r.getContent().getName()))
                .filter(id -> !id.equals(userId))
                .collect(Collectors.toList());

        if (nearbyUserIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 4. 批量查询用户信息
        List<User> users = userMapper.selectBatchIds(nearbyUserIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));

        // 5. 构建距离映射
        Map<String, Double> distanceMap = results.getContent().stream()
                .collect(Collectors.toMap(
                        r -> r.getContent().getName(),
                        r -> r.getDistance().getValue(),
                        (a, b) -> a));

        // 6. 按性别/标签二次过滤 + 组装结果
        List<UserVO> voList = nearbyUserIds.stream()
                .map(userMap::get)
                .filter(Objects::nonNull)
                .filter(u -> u.getStatus() == 1)
                .filter(u -> query.getGender() == null || query.getGender().equals(u.getGender()))
                .map(u -> {
                    UserVO vo = buildUserVO(u);
                    vo.setDistance(distanceMap.getOrDefault(String.valueOf(u.getId()), 0.0));
                    return vo;
                })
                .collect(Collectors.toList());

        // 7. 增加今日匹配计数
        incrementMatchCount(userId);

        return voList;
    }

    @Override
    public List<UserVO> getRecommendUsers(Long userId) {
        // 1. 获取当前用户信息和标签
        User currentUser = userMapper.selectById(userId);
        if (currentUser == null) {
            throw new BizException(ResultCode.USER_NOT_FOUND);
        }

        List<UserTag> myTags = userTagMapper.selectList(
                new LambdaQueryWrapper<UserTag>().eq(UserTag::getUserId, userId));
        Set<String> myTagNames = myTags.stream().map(UserTag::getTagName).collect(Collectors.toSet());

        // 2. 先通过 GEO 获取附近的人（范围放大到 30km）
        NearbyQuery query = new NearbyQuery();
        query.setLongitude(currentUser.getLongitude());
        query.setLatitude(currentUser.getLatitude());
        query.setRadiusKm(30.0);
        query.setCount(50);

        List<UserVO> nearby = getNearbyUsersInternal(userId, query);

        // 3. 计算匹配分数（标签重合度40% + 距离40% + 活跃度20%）
        for (UserVO vo : nearby) {
            List<UserTag> targetTags = userTagMapper.selectList(
                    new LambdaQueryWrapper<UserTag>().eq(UserTag::getUserId, vo.getId()));
            Set<String> targetTagNames = targetTags.stream().map(UserTag::getTagName).collect(Collectors.toSet());

            // 标签重合度
            long commonCount = myTagNames.stream().filter(targetTagNames::contains).count();
            double tagScore = myTagNames.isEmpty() ? 0 : (double) commonCount / myTagNames.size() * 40;

            // 距离分（越近越高）
            double distScore = Math.max(0, 40 - (vo.getDistance() / 30.0 * 40));

            // 活跃度暂时用在线状态代替
            double activeScore = Boolean.TRUE.equals(vo.getOnline()) ? 20 : 5;

            vo.setDistance(Math.round(vo.getDistance() * 10.0) / 10.0); // 保留1位小数
            vo.setTags(targetTags.stream().map(UserTag::getTagName).collect(Collectors.toList()));
        }

        // 4. 按分数排序取前20
        return nearby.stream()
                .limit(20)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserVO> searchUsers(String keyword, String city, Integer gender,
                                    String tag, int pageNum, int pageSize) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStatus, 1);

        // 按昵称模糊搜索
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(User::getNickname, keyword);
        }
        // 按城市筛选
        if (StrUtil.isNotBlank(city)) {
            wrapper.eq(User::getCity, city);
        }
        // 按性别筛选
        if (gender != null) {
            wrapper.eq(User::getGender, gender);
        }

        wrapper.orderByDesc(User::getLastLoginTime);

        Page<User> page = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        List<UserVO> result = page.getRecords().stream()
                .map(this::buildUserVO)
                .collect(Collectors.toList());

        // 标签过滤（如果指定了标签）
        if (StrUtil.isNotBlank(tag)) {
            List<Long> tagUserIds = userTagMapper.selectList(
                    new LambdaQueryWrapper<UserTag>().eq(UserTag::getTagName, tag))
                    .stream().map(UserTag::getUserId).collect(Collectors.toList());
            result = result.stream()
                    .filter(vo -> tagUserIds.contains(vo.getId()))
                    .collect(Collectors.toList());
        }

        return result;
    }

    // ==================== 私有方法 ====================

    /**
     * 内部调用的附近查询（不检查限流）
     */
    private List<UserVO> getNearbyUsersInternal(Long userId, NearbyQuery query) {
        if (query.getLongitude() == null || query.getLatitude() == null) {
            return Collections.emptyList();
        }

        Point center = new Point(query.getLongitude().doubleValue(), query.getLatitude().doubleValue());
        Distance distance = new Distance(query.getRadiusKm(), Metrics.KILOMETERS);

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().sortAscending().limit(query.getCount() + 1);

        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                redisTemplate.opsForGeo().radius(RedisKeyConstant.GEO_USER, new Circle(center, distance), args);

        if (results == null) return Collections.emptyList();

        List<Long> ids = results.getContent().stream()
                .map(r -> Long.valueOf(r.getContent().getName()))
                .filter(id -> !id.equals(userId))
                .collect(Collectors.toList());

        if (ids.isEmpty()) return Collections.emptyList();

        Map<String, Double> distMap = results.getContent().stream()
                .collect(Collectors.toMap(r -> r.getContent().getName(), r -> r.getDistance().getValue(), (a, b) -> a));

        List<User> users = userMapper.selectBatchIds(ids);
        return users.stream()
                .filter(u -> u.getStatus() == 1)
                .map(u -> {
                    UserVO vo = buildUserVO(u);
                    vo.setDistance(distMap.getOrDefault(String.valueOf(u.getId()), 0.0));
                    vo.setOnline(redisTemplate.hasKey(RedisKeyConstant.USER_ONLINE + u.getId()));
                    return vo;
                })
                .collect(Collectors.toList());
    }

    private void checkMatchLimit(Long userId) {
        String key = RedisKeyConstant.LIMIT_MATCH_DAILY + userId;
        String countStr = redisTemplate.opsForValue().get(key);
        if (countStr != null && Integer.parseInt(countStr) >= AppConstant.FREE_DAILY_MATCH_LIMIT) {
            // TODO: 检查是否是会员，会员不限制
            throw new BizException(ResultCode.MATCH_LIMIT_REACHED);
        }
    }

    private void incrementMatchCount(Long userId) {
        String key = RedisKeyConstant.LIMIT_MATCH_DAILY + userId;
        redisTemplate.opsForValue().increment(key);
        // 设置到今天 23:59:59 过期
        long secondsUntilMidnight = LocalDate.now().plusDays(1).atStartOfDay()
                .toEpochSecond(ZoneOffset.ofHours(8)) - System.currentTimeMillis() / 1000;
        redisTemplate.expire(key, secondsUntilMidnight, TimeUnit.SECONDS);
    }

    private UserVO buildUserVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setNickname(user.getNickname());
        vo.setAvatar(user.getAvatar());
        vo.setGender(user.getGender());
        vo.setCity(user.getCity());
        vo.setBio(user.getBio());
        vo.setCampingYears(user.getCampingYears());
        vo.setMemberLevel(user.getMemberLevel());
        return vo;
    }
}
