package com.luyingdazi.api.controller;

import com.luyingdazi.common.result.Result;
import com.luyingdazi.common.util.UserContext;
import com.luyingdazi.model.query.NearbyQuery;
import com.luyingdazi.model.vo.UserVO;
import com.luyingdazi.service.match.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 匹配接口（附近的人、同城推荐、搜索）
 *
 * @author luyingdazi
 */
@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    /**
     * 附近的人
     */
    @PostMapping("/nearby")
    public Result<List<UserVO>> getNearbyUsers(@RequestBody NearbyQuery query) {
        return Result.success(matchService.getNearbyUsers(UserContext.getUserId(), query));
    }

    /**
     * 智能推荐（同城匹配）
     */
    @GetMapping("/recommend")
    public Result<List<UserVO>> getRecommendUsers() {
        return Result.success(matchService.getRecommendUsers(UserContext.getUserId()));
    }

    /**
     * 搜索用户
     */
    @GetMapping("/search")
    public Result<List<UserVO>> searchUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Integer gender,
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize) {
        return Result.success(matchService.searchUsers(keyword, city, gender, tag, pageNum, pageSize));
    }
}
