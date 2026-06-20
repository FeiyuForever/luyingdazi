package com.luyingdazi.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 动态实体
 *
 * @author luyingdazi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_post", autoResultMap = true)
public class Post extends BaseEntity {

    /** 发布者ID */
    private Long userId;

    /** 文字内容 */
    private String content;

    /** 图片URL列表 */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> images;

    /** 定位名称 */
    private String locationName;

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

    /** 点赞数 */
    private Integer likeCount;

    /** 评论数 */
    private Integer commentCount;

    /** 是否置顶 */
    private Integer isTop;

    /** 置顶到期时间 */
    private LocalDateTime topExpireTime;

    /** 状态：0删除 1正常 2审核中 3违规 */
    private Integer status;
}
