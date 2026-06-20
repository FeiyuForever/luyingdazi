package com.luyingdazi.api.controller;

import com.luyingdazi.common.result.Result;
import com.luyingdazi.common.util.UserContext;
import com.luyingdazi.model.dto.LocationDTO;
import com.luyingdazi.model.dto.WxLoginDTO;
import com.luyingdazi.model.vo.LoginVO;
import com.luyingdazi.model.vo.UserVO;
import com.luyingdazi.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户接口
 *
 * @author luyingdazi
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 微信登录
     */
    @PostMapping("/wx-login")
    public Result<LoginVO> wxLogin(@RequestBody @Valid WxLoginDTO dto) {
        return Result.success(userService.wxLogin(dto));
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public Result<UserVO> getUserInfo() {
        return Result.success(userService.getUserInfo(UserContext.getUserId()));
    }

    /**
     * 获取指定用户主页
     */
    @GetMapping("/profile/{userId}")
    public Result<UserVO> getUserProfile(@PathVariable Long userId) {
        return Result.success(userService.getUserProfile(userId, UserContext.getUserId()));
    }

    /**
     * 更新个人资料
     */
    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody UserVO vo) {
        userService.updateProfile(UserContext.getUserId(), vo);
        return Result.success();
    }

    /**
     * 上报位置
     */
    @PostMapping("/location")
    public Result<Void> reportLocation(@RequestBody @Valid LocationDTO dto) {
        userService.reportLocation(UserContext.getUserId(), dto);
        return Result.success();
    }
}
