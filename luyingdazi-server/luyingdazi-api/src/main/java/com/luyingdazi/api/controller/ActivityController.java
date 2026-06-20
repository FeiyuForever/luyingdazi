package com.luyingdazi.api.controller;

import com.luyingdazi.common.result.PageResult;
import com.luyingdazi.common.result.Result;
import com.luyingdazi.common.util.UserContext;
import com.luyingdazi.model.entity.Activity;
import com.luyingdazi.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return Result.success(activityService.getActivityDetail(activityId));
    }

    /**
     * 活动列表
     */
    @GetMapping("/list")
    public Result<PageResult<Activity>> getList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String city) {
        return Result.success(activityService.getActivityList(pageNum, pageSize, city));
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
}
