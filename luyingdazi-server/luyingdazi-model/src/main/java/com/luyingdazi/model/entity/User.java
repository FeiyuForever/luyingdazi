package com.luyingdazi.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author luyingdazi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class User extends BaseEntity {

    /** 微信小程序openid */
    private String openid;

    /** 微信unionid */
    private String unionid;

    /** 手机号 */
    private String phone;

    /** 昵称 */
    private String nickname;

    /** 头像URL */
    private String avatar;

    /** 性别：0未知 1男 2女 */
    private Integer gender;

    /** 生日 */
    private LocalDate birthday;

    /** 城市 */
    private String city;

    /** 省份 */
    private String province;

    /** 个人简介 */
    private String bio;

    /** 露营年限：0新手 1一年内 2一到三年 3三年以上 */
    private Integer campingYears;

    /** 信用评分 */
    private Integer creditScore;

    /** 会员等级：0普通 1月度 2季度 3年度 */
    private Integer memberLevel;

    /** 会员到期时间 */
    private LocalDateTime memberExpireTime;

    /** 我的邀请码 */
    private String inviteCode;

    /** 邀请人ID */
    private Long invitedBy;

    /** 金币余额 */
    private Long coinBalance;

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;

    /** 状态：0封禁 1正常 */
    private Integer status;
}
