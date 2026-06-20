package com.luyingdazi.service.user.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.luyingdazi.common.constant.AppConstant;
import com.luyingdazi.common.constant.RedisKeyConstant;
import com.luyingdazi.common.exception.BizException;
import com.luyingdazi.common.result.ResultCode;
import com.luyingdazi.model.dto.LocationDTO;
import com.luyingdazi.model.dto.WxLoginDTO;
import com.luyingdazi.model.entity.User;
import com.luyingdazi.model.vo.LoginVO;
import com.luyingdazi.model.vo.UserVO;
import com.luyingdazi.mapper.UserMapper;
import com.luyingdazi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现
 *
 * @author luyingdazi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final StringRedisTemplate redisTemplate;

    @Value("${wx.miniapp.app-id}")
    private String wxAppId;

    @Value("${wx.miniapp.app-secret}")
    private String wxAppSecret;

    @Override
    public LoginVO wxLogin(WxLoginDTO dto) {
        // 开发环境 mock 登录支持
        if ("h5_dev_mock_code".equals(dto.getCode())) {
            return mockDevLogin();
        }

        // 1. 调微信接口换取 openid
        String openid = getWxOpenid(dto.getCode());
        if (StrUtil.isBlank(openid)) {
            throw new BizException(ResultCode.WX_LOGIN_FAIL);
        }

        // 2. 查询用户是否已注册
        User user = userMapper.selectByOpenid(openid);
        boolean isNew = (user == null);

        // 3. 新用户自动注册
        if (isNew) {
            user = new User();
            user.setOpenid(openid);
            user.setNickname(StrUtil.isNotBlank(dto.getNickname()) ? dto.getNickname() : "露营新人");
            user.setAvatar(dto.getAvatar());
            user.setGender(0);
            user.setCampingYears(0);
            user.setCreditScore(100);
            user.setMemberLevel(0);
            user.setCoinBalance(0L);
            user.setStatus(1);
            user.setInviteCode(generateInviteCode());
            user.setLastLoginTime(LocalDateTime.now());
            userMapper.insert(user);
            log.info("新用户注册: userId={}, openid={}", user.getId(), openid);
        } else {
            // 老用户更新登录时间
            userMapper.update(null, new LambdaUpdateWrapper<User>()
                    .eq(User::getId, user.getId())
                    .set(User::getLastLoginTime, LocalDateTime.now()));
        }

        // 4. 生成 Token 存入 Redis
        String token = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(
                RedisKeyConstant.USER_TOKEN + token,
                String.valueOf(user.getId()),
                AppConstant.TOKEN_EXPIRE_SECONDS,
                TimeUnit.SECONDS
        );

        // 5. 组装返回结果
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setIsNew(isNew);
        loginVO.setUserInfo(buildUserVO(user));
        return loginVO;
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BizException(ResultCode.USER_NOT_FOUND);
        }
        return buildUserVO(user);
    }

    @Override
    public void updateProfile(Long userId, UserVO vo) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, userId);

        if (StrUtil.isNotBlank(vo.getNickname())) {
            wrapper.set(User::getNickname, vo.getNickname());
        }
        if (StrUtil.isNotBlank(vo.getAvatar())) {
            wrapper.set(User::getAvatar, vo.getAvatar());
        }
        if (vo.getGender() != null) {
            wrapper.set(User::getGender, vo.getGender());
        }
        if (StrUtil.isNotBlank(vo.getCity())) {
            wrapper.set(User::getCity, vo.getCity());
        }
        if (StrUtil.isNotBlank(vo.getBio())) {
            wrapper.set(User::getBio, vo.getBio());
        }
        if (vo.getCampingYears() != null) {
            wrapper.set(User::getCampingYears, vo.getCampingYears());
        }

        userMapper.update(null, wrapper);

        // 清除缓存
        redisTemplate.delete(RedisKeyConstant.USER_INFO + userId);
    }

    @Override
    public void reportLocation(Long userId, LocationDTO dto) {
        // 1. 更新数据库位置
        userMapper.update(null, new LambdaUpdateWrapper<User>()
                .eq(User::getId, userId)
                .set(User::getLongitude, dto.getLongitude())
                .set(User::getLatitude, dto.getLatitude())
                .set(dto.getCity() != null, User::getCity, dto.getCity()));

        // 2. 写入 Redis GEO（用于附近的人查询）
        redisTemplate.opsForGeo().add(
                RedisKeyConstant.GEO_USER,
                new org.springframework.data.geo.Point(
                        dto.getLongitude().doubleValue(),
                        dto.getLatitude().doubleValue()),
                String.valueOf(userId)
        );
    }

    @Override
    public UserVO getUserProfile(Long targetUserId, Long currentUserId) {
        User user = userMapper.selectById(targetUserId);
        if (user == null) {
            throw new BizException(ResultCode.USER_NOT_FOUND);
        }

        UserVO vo = buildUserVO(user);

        // 查是否已关注
        if (currentUserId != null && !currentUserId.equals(targetUserId)) {
            Long count = userMapper.selectCount(new LambdaQueryWrapper<User>()
                    // 这里实际应该查 t_follow 表，暂时简化
            );
            vo.setFollowed(false); // TODO: 接入 FollowMapper 后完善
        }

        return vo;
    }

    // ==================== 私有方法 ====================

    /**
     * 调用微信 code2session 接口获取 openid
     */
    private String getWxOpenid(String code) {
        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                wxAppId, wxAppSecret, code);
        try {
            String response = HttpUtil.get(url, 5000);
            JSONObject json = JSONUtil.parseObj(response);
            log.debug("微信登录响应: {}", response);

            if (json.containsKey("openid")) {
                return json.getStr("openid");
            }
            log.error("微信登录失败: {}", response);
            return null;
        } catch (Exception e) {
            log.error("调用微信接口异常", e);
            return null;
        }
    }

    /**
     * 生成6位邀请码
     */
    private String generateInviteCode() {
        return IdUtil.nanoId(6).toUpperCase();
    }

    /**
     * 构建 UserVO
     */
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

    /**
     * 开发环境 Mock 登录（不走微信接口）
     */
    private LoginVO mockDevLogin() {
        String mockOpenid = "dev_mock_openid_001";
        User user = userMapper.selectByOpenid(mockOpenid);
        boolean isNew = (user == null);

        if (isNew) {
            user = new User();
            user.setOpenid(mockOpenid);
            user.setNickname("露营达人");
            user.setGender(1);
            user.setCity("上海");
            user.setBio("热爱户外，享受自然");
            user.setCampingYears(2);
            user.setCreditScore(100);
            user.setMemberLevel(0);
            user.setCoinBalance(0L);
            user.setStatus(1);
            user.setInviteCode(generateInviteCode());
            user.setLastLoginTime(LocalDateTime.now());
            userMapper.insert(user);
            log.info("开发环境Mock用户注册: userId={}", user.getId());
        }

        String token = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(
                RedisKeyConstant.USER_TOKEN + token,
                String.valueOf(user.getId()),
                AppConstant.TOKEN_EXPIRE_SECONDS,
                TimeUnit.SECONDS
        );

        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setIsNew(isNew);
        loginVO.setUserInfo(buildUserVO(user));
        return loginVO;
    }
}
