package com.luyingdazi.service.activity.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luyingdazi.common.exception.BizException;
import com.luyingdazi.common.result.PageResult;
import com.luyingdazi.common.result.ResultCode;
import com.luyingdazi.mapper.ActivityMapper;
import com.luyingdazi.mapper.ActivityMemberMapper;
import com.luyingdazi.model.entity.Activity;
import com.luyingdazi.model.entity.ActivityMember;
import com.luyingdazi.service.activity.ActivityService;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动服务实现
 *
 * @author luyingdazi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityMapper activityMapper;
    private final ActivityMemberMapper activityMemberMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createActivity(Long userId, Activity activity) {
        if (StrUtil.isBlank(activity.getTitle()) || StrUtil.isBlank(activity.getLocationName())
                || activity.getStartTime() == null || activity.getEndTime() == null) {
            throw new BizException(ResultCode.PARAM_MISSING);
        }
        if (!activity.getEndTime().isAfter(activity.getStartTime())
                || activity.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BizException("活动时间不正确");
        }
        if (activity.getMaxMembers() == null || activity.getMaxMembers() < 0) {
            activity.setMaxMembers(0);
        }
        activity.setUserId(userId);
        activity.setCurrentMembers(1);
        activity.setStatus(1);
        activityMapper.insert(activity);

        // 发起人自动加入
        ActivityMember member = new ActivityMember();
        member.setActivityId(activity.getId());
        member.setUserId(userId);
        member.setRole(1); // 发起人
        member.setStatus(1);
        activityMemberMapper.insert(member);

        log.info("用户{}创建活动: activityId={}, title={}", userId, activity.getId(), activity.getTitle());
        return activity.getId();
    }

    @Override
    public Activity getActivityDetail(Long activityId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new BizException(ResultCode.ACTIVITY_NOT_FOUND);
        }
        return activity;
    }

    @Override
    public PageResult<Activity> getActivityList(int pageNum, int pageSize, String city) {
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Activity::getStatus, 1, 2, 3); // 报名中、已满、进行中
        if (StrUtil.isNotBlank(city)) {
            wrapper.like(Activity::getLocationName, city);
        }
        wrapper.orderByDesc(Activity::getCreatedAt);

        Page<Activity> page = activityMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return PageResult.of(page.getRecords(), page.getTotal(), pageNum, pageSize);
    }

    @Override
    public List<Activity> getUserActivities(Long userId, boolean created) {
        return created
                ? activityMapper.selectCreatedByUserId(userId)
                : activityMapper.selectJoinedByUserId(userId);
    }

    @Override
    public boolean isJoined(Long userId, Long activityId) {
        return activityMemberMapper.selectCount(new LambdaQueryWrapper<ActivityMember>()
                .eq(ActivityMember::getActivityId, activityId)
                .eq(ActivityMember::getUserId, userId)
                .eq(ActivityMember::getStatus, 1)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void joinActivity(Long userId, Long activityId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new BizException(ResultCode.ACTIVITY_NOT_FOUND);
        }
        if (activity.getStatus() != 1) {
            throw new BizException(ResultCode.ACTIVITY_EXPIRED);
        }
        if (activity.getMaxMembers() > 0 && activity.getCurrentMembers() >= activity.getMaxMembers()) {
            throw new BizException(ResultCode.ACTIVITY_FULL);
        }

        // 检查是否已有报名记录。取消后再次报名时复用旧记录，避免唯一键冲突。
        ActivityMember existing = activityMemberMapper.selectOne(new LambdaQueryWrapper<ActivityMember>()
                .eq(ActivityMember::getActivityId, activityId)
                .eq(ActivityMember::getUserId, userId));
        if (existing != null && existing.getStatus() == 1) {
            throw new BizException(ResultCode.ALREADY_JOINED);
        }

        if (existing != null) {
            existing.setStatus(1);
            existing.setRole(2);
            existing.setJoinedAt(LocalDateTime.now());
            activityMemberMapper.updateById(existing);
        } else {
            ActivityMember member = new ActivityMember();
            member.setActivityId(activityId);
            member.setUserId(userId);
            member.setRole(2); // 参与者
            member.setStatus(1);
            activityMemberMapper.insert(member);
        }

        // 更新人数
        activityMapper.update(null, new LambdaUpdateWrapper<Activity>()
                .eq(Activity::getId, activityId)
                .setSql("current_members = current_members + 1"));

        // 如果满员，更新状态
        if (activity.getMaxMembers() > 0 && activity.getCurrentMembers() + 1 >= activity.getMaxMembers()) {
            activityMapper.update(null, new LambdaUpdateWrapper<Activity>()
                    .eq(Activity::getId, activityId)
                    .set(Activity::getStatus, 2)); // 已满
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void quitActivity(Long userId, Long activityId) {
        int affected = activityMemberMapper.update(null, new LambdaUpdateWrapper<ActivityMember>()
                .eq(ActivityMember::getActivityId, activityId)
                .eq(ActivityMember::getUserId, userId)
                .eq(ActivityMember::getRole, 2) // 只有参与者能退出
                .eq(ActivityMember::getStatus, 1)
                .set(ActivityMember::getStatus, 0));
        if (affected == 0) {
            throw new BizException("您尚未报名该活动");
        }

        activityMapper.update(null, new LambdaUpdateWrapper<Activity>()
                .eq(Activity::getId, activityId)
                .gt(Activity::getCurrentMembers, 0)
                .setSql("current_members = current_members - 1"));

        // 满员活动有人退出后恢复为可报名。
        activityMapper.update(null, new LambdaUpdateWrapper<Activity>()
                .eq(Activity::getId, activityId)
                .eq(Activity::getStatus, 2)
                .set(Activity::getStatus, 1));
    }

    @Override
    public void cancelActivity(Long userId, Long activityId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            throw new BizException(ResultCode.ACTIVITY_NOT_FOUND);
        }
        if (!activity.getUserId().equals(userId)) {
            throw new BizException(ResultCode.PERMISSION_DENIED);
        }

        activityMapper.update(null, new LambdaUpdateWrapper<Activity>()
                .eq(Activity::getId, activityId)
                .set(Activity::getStatus, 0)); // 取消
    }
}
