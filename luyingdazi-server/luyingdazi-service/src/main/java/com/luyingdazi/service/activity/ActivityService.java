package com.luyingdazi.service.activity;

import com.luyingdazi.common.result.PageResult;
import com.luyingdazi.model.entity.Activity;

import java.util.List;

/**
 * 活动服务接口
 *
 * @author luyingdazi
 */
public interface ActivityService {

    /**
     * 创建活动
     *
     * @param userId   发起人ID
     * @param activity 活动信息
     * @return 活动ID
     */
    Long createActivity(Long userId, Activity activity);

    /**
     * 获取活动详情
     *
     * @param activityId 活动ID
     * @return 活动详情（含报名人列表）
     */
    Activity getActivityDetail(Long activityId);

    /**
     * 获取活动列表（按距离/时间排序）
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param city     城市筛选（可选）
     * @return 分页结果
     */
    PageResult<Activity> getActivityList(int pageNum, int pageSize, String city);

    /** 获取当前用户参加或发起的活动 */
    List<Activity> getUserActivities(Long userId, boolean created);

    /** 当前用户是否已参加活动 */
    boolean isJoined(Long userId, Long activityId);

    /**
     * 报名参加活动
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     */
    void joinActivity(Long userId, Long activityId);

    /**
     * 取消报名
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     */
    void quitActivity(Long userId, Long activityId);

    /**
     * 取消活动（仅发起人）
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     */
    void cancelActivity(Long userId, Long activityId);
}
