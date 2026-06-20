package com.luyingdazi.service.user;

import com.luyingdazi.model.dto.LocationDTO;
import com.luyingdazi.model.dto.WxLoginDTO;
import com.luyingdazi.model.vo.LoginVO;
import com.luyingdazi.model.vo.UserVO;

/**
 * 用户服务接口
 *
 * @author luyingdazi
 */
public interface UserService {

    /**
     * 微信登录（自动注册）
     *
     * @param dto 微信登录参数
     * @return 登录结果（含Token和用户信息）
     */
    LoginVO wxLogin(WxLoginDTO dto);

    /**
     * 获取当前用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVO getUserInfo(Long userId);

    /**
     * 更新用户资料
     *
     * @param userId 用户ID
     * @param vo     更新内容
     */
    void updateProfile(Long userId, UserVO vo);

    /**
     * 上报用户位置
     *
     * @param userId 用户ID
     * @param dto    位置信息
     */
    void reportLocation(Long userId, LocationDTO dto);

    /**
     * 根据ID获取用户主页信息（含是否关注等）
     *
     * @param targetUserId 目标用户ID
     * @param currentUserId 当前用户ID
     * @return 用户主页信息
     */
    UserVO getUserProfile(Long targetUserId, Long currentUserId);
}
