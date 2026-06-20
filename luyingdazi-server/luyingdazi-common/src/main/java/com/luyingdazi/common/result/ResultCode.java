package com.luyingdazi.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一状态码枚举
 *
 * @author luyingdazi
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),

    // 认证相关 1001-1099
    UNAUTHORIZED(1001, "未登录或登录已过期"),
    TOKEN_INVALID(1002, "Token无效"),
    TOKEN_EXPIRED(1003, "Token已过期"),
    ACCOUNT_DISABLED(1004, "账号已被封禁"),

    // 参数校验 1100-1199
    PARAM_ERROR(1100, "参数错误"),
    PARAM_MISSING(1101, "缺少必要参数"),

    // 业务错误 2000-2999
    USER_NOT_FOUND(2001, "用户不存在"),
    USER_ALREADY_EXISTS(2002, "用户已存在"),
    POST_NOT_FOUND(2003, "动态不存在"),
    ACTIVITY_NOT_FOUND(2004, "活动不存在"),
    ACTIVITY_FULL(2005, "活动人数已满"),
    ACTIVITY_EXPIRED(2006, "活动已结束"),
    ALREADY_FOLLOWED(2007, "已关注该用户"),
    NOT_FOLLOWED(2008, "未关注该用户"),
    ALREADY_LIKED(2009, "已点赞"),
    ALREADY_JOINED(2010, "已报名该活动"),
    CONTENT_ILLEGAL(2011, "内容包含违规信息"),
    CHAT_LIMIT_REACHED(2012, "今日私信次数已达上限"),
    MATCH_LIMIT_REACHED(2013, "今日匹配刷新次数已达上限"),

    // 权限相关 3000-3099
    MEMBER_REQUIRED(3001, "该功能需要开通会员"),
    PERMISSION_DENIED(3002, "无权限执行此操作"),

    // 第三方服务 4000-4099
    WX_LOGIN_FAIL(4001, "微信登录失败"),
    OSS_UPLOAD_FAIL(4002, "文件上传失败"),
    PAY_FAIL(4003, "支付失败"),

    // 系统错误 9000-9099
    SYSTEM_ERROR(9000, "系统异常，请稍后重试"),
    RATE_LIMIT(9001, "请求过于频繁，请稍后重试");

    private final int code;
    private final String msg;
}
