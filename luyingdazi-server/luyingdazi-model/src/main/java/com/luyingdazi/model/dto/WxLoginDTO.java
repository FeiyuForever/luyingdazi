package com.luyingdazi.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 微信登录请求参数
 *
 * @author luyingdazi
 */
@Data
public class WxLoginDTO {

    /** wx.login 获取的临时凭证 */
    @NotBlank(message = "code不能为空")
    private String code;

    /** 用户昵称（首次登录时可传） */
    private String nickname;

    /** 用户头像（首次登录时可传） */
    private String avatar;
}
