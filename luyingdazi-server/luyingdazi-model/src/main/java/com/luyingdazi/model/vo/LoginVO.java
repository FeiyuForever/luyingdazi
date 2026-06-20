package com.luyingdazi.model.vo;

import lombok.Data;

/**
 * 登录响应
 *
 * @author luyingdazi
 */
@Data
public class LoginVO {

    /** 登录Token */
    private String token;

    /** 是否新用户（前端据此跳转完善资料页） */
    private Boolean isNew;

    /** 用户信息 */
    private UserVO userInfo;
}
