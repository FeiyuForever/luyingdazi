package com.luyingdazi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 关注关系实体
 *
 * @author luyingdazi
 */
@Data
@TableName("t_follow")
public class Follow implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关注者 */
    private Long userId;

    /** 被关注者 */
    private Long followUserId;

    private LocalDateTime createdAt;
}
