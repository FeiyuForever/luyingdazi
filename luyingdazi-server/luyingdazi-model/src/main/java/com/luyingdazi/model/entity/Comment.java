package com.luyingdazi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论实体
 *
 * @author luyingdazi
 */
@Data
@TableName("t_comment")
public class Comment implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long postId;

    private Long userId;

    /** 父评论ID（0为一级评论） */
    private Long parentId;

    /** 回复的用户ID */
    private Long replyUserId;

    private String content;

    /** 0删除 1正常 */
    private Integer status;

    private LocalDateTime createdAt;
}
