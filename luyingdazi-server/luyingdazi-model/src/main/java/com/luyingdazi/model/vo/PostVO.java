package com.luyingdazi.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 动态视图（含用户信息）
 *
 * @author luyingdazi
 */
@Data
public class PostVO {

    private Long id;
    private Long userId;
    private String content;
    private List<String> images;
    private String locationName;
    private Integer likeCount;
    private Integer commentCount;
    private Integer isTop;
    private LocalDateTime createdAt;

    /** 发布者昵称 */
    private String nickname;

    /** 发布者头像 */
    private String avatar;

    /** 当前用户是否已点赞 */
    private Boolean liked;
}
