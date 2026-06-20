package com.luyingdazi.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 用户信息视图（返回给前端）
 *
 * @author luyingdazi
 */
@Data
public class UserVO {

    private Long id;

    private String nickname;

    private String avatar;

    private Integer gender;

    private String city;

    private String bio;

    private Integer campingYears;

    /** 露营标签列表 */
    private List<String> tags;

    /** 距离（km，附近的人场景使用） */
    private Double distance;

    /** 是否已关注 */
    private Boolean followed;

    /** 粉丝数 */
    private Integer fansCount;

    /** 关注数 */
    private Integer followCount;

    /** 动态数 */
    private Integer postCount;

    /** 是否在线 */
    private Boolean online;

    /** 会员等级 */
    private Integer memberLevel;
}
