package com.luyingdazi.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.luyingdazi.common.exception.BizException;
import com.luyingdazi.common.result.Result;
import com.luyingdazi.common.result.ResultCode;
import com.luyingdazi.common.util.UserContext;
import com.luyingdazi.mapper.FollowMapper;
import com.luyingdazi.mapper.UserMapper;
import com.luyingdazi.model.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 关注接口
 *
 * @author luyingdazi
 */
@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowMapper followMapper;
    private final UserMapper userMapper;

    /**
     * 关注/取消关注（切换）
     */
    @PostMapping("/toggle/{targetUserId}")
    public Result<Boolean> toggleFollow(@PathVariable Long targetUserId) {
        Long userId = UserContext.getUserId();
        if (userId.equals(targetUserId)) {
            throw new BizException("不能关注自己");
        }

        Follow existing = followMapper.selectOne(
                new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getUserId, userId)
                        .eq(Follow::getFollowUserId, targetUserId));

        if (existing != null) {
            // 取消关注
            followMapper.deleteById(existing.getId());
            return Result.success(false);
        } else {
            // 关注
            Follow follow = new Follow();
            follow.setUserId(userId);
            follow.setFollowUserId(targetUserId);
            follow.setCreatedAt(LocalDateTime.now());
            followMapper.insert(follow);
            return Result.success(true);
        }
    }

    /**
     * 查询是否已关注
     */
    @GetMapping("/check/{targetUserId}")
    public Result<Boolean> checkFollow(@PathVariable Long targetUserId) {
        Long userId = UserContext.getUserId();
        Long count = followMapper.selectCount(
                new LambdaQueryWrapper<Follow>()
                        .eq(Follow::getUserId, userId)
                        .eq(Follow::getFollowUserId, targetUserId));
        return Result.success(count > 0);
    }

    /**
     * 获取关注数和粉丝数
     */
    @GetMapping("/count/{targetUserId}")
    public Result<java.util.Map<String, Long>> getFollowCount(@PathVariable Long targetUserId) {
        Long followCount = followMapper.selectCount(
                new LambdaQueryWrapper<Follow>().eq(Follow::getUserId, targetUserId));
        Long fansCount = followMapper.selectCount(
                new LambdaQueryWrapper<Follow>().eq(Follow::getFollowUserId, targetUserId));

        java.util.Map<String, Long> map = new java.util.HashMap<>();
        map.put("followCount", followCount);
        map.put("fansCount", fansCount);
        return Result.success(map);
    }

    /**
     * 获取关注列表或粉丝列表
     */
    @GetMapping("/list/{userId}")
    public Result<java.util.List<java.util.Map<String, Object>>> getFollowList(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "follow") String type) {

        java.util.List<Follow> follows;
        if ("fans".equals(type)) {
            follows = followMapper.selectList(
                    new LambdaQueryWrapper<Follow>().eq(Follow::getFollowUserId, userId));
        } else {
            follows = followMapper.selectList(
                    new LambdaQueryWrapper<Follow>().eq(Follow::getUserId, userId));
        }

        if (follows.isEmpty()) {
            return Result.success(java.util.Collections.emptyList());
        }

        java.util.Set<Long> userIds = follows.stream()
                .map(f -> "fans".equals(type) ? f.getUserId() : f.getFollowUserId())
                .collect(java.util.stream.Collectors.toSet());

        java.util.List<com.luyingdazi.model.entity.User> users = userMapper.selectBatchIds(userIds);

        java.util.List<java.util.Map<String, Object>> result = users.stream().map(u -> {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("id", u.getId());
            map.put("nickname", u.getNickname());
            map.put("avatar", u.getAvatar());
            map.put("bio", u.getBio());
            return map;
        }).collect(java.util.stream.Collectors.toList());

        return Result.success(result);
    }
}
