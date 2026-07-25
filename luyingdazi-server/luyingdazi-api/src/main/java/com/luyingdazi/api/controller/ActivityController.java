package com.luyingdazi.api.controller;

import com.luyingdazi.api.service.OssUrlService;
import com.luyingdazi.common.result.PageResult;
import com.luyingdazi.common.result.Result;
import com.luyingdazi.common.util.UserContext;
import com.luyingdazi.model.entity.Activity;
import com.luyingdazi.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 活动接口
 *
 * @author luyingdazi
 */
@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final OssUrlService ossUrlService;

    /**
     * 创建活动
     */
    @PostMapping("/create")
    public Result<Long> create(@RequestBody Activity activity) {
        return Result.success(activityService.createActivity(UserContext.getUserId(), activity));
    }

    /**
     * 活动详情
     */
    @GetMapping("/{activityId}")
    public Result<Activity> getDetail(@PathVariable Long activityId) {
        return Result.success(signCover(
                activityService.getActivityDetail(activityId)));
    }

    /**
     * 活动列表
     */
    @GetMapping("/list")
    public Result<PageResult<Activity>> getList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String city) {
        PageResult<Activity> page = activityService.getActivityList(pageNum, pageSize, city);
        page.getList().forEach(this::signCover);
        return Result.success(page);
    }

    /** 当前用户参加或发起的活动 */
    @GetMapping("/mine")
    public Result<List<Activity>> getMine(
            @RequestParam(defaultValue = "joined") String type) {
        List<Activity> activities = activityService.getUserActivities(
                UserContext.getUserId(), "created".equals(type));
        activities.forEach(this::signCover);
        return Result.success(activities);
    }

    /** 当前用户是否已报名 */
    @GetMapping("/joined/{activityId}")
    public Result<Boolean> isJoined(@PathVariable Long activityId) {
        return Result.success(activityService.isJoined(
                UserContext.getUserId(), activityId));
    }

    /**
     * 报名参加
     */
    @PostMapping("/join/{activityId}")
    public Result<Void> join(@PathVariable Long activityId) {
        activityService.joinActivity(UserContext.getUserId(), activityId);
        return Result.success();
    }

    /**
     * 取消报名
     */
    @PostMapping("/quit/{activityId}")
    public Result<Void> quit(@PathVariable Long activityId) {
        activityService.quitActivity(UserContext.getUserId(), activityId);
        return Result.success();
    }

    /**
     * 取消活动（仅发起人）
     */
    @PostMapping("/cancel/{activityId}")
    public Result<Void> cancel(@PathVariable Long activityId) {
        activityService.cancelActivity(UserContext.getUserId(), activityId);
        return Result.success();
    }

    private Activity signCover(Activity activity) {
        if (activity != null) {
            activity.setCoverImage(
                    ossUrlService.toAccessibleUrl(activity.getCoverImage()));
        }
        return activity;
    }
}
