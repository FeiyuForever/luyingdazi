package com.luyingdazi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户标签实体
 *
 * @author luyingdazi
 */
@Data
@TableName("t_user_tag")
public class UserTag implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String tagName;

    private LocalDateTime createdAt;
}
