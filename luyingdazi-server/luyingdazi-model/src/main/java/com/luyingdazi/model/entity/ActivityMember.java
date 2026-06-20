package com.luyingdazi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动报名实体
 *
 * @author luyingdazi
 */
@Data
@TableName("t_activity_member")
public class ActivityMember implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long activityId;

    private Long userId;

    /** 1发起人 2参与者 */
    private Integer role;

    /** 0取消 1已报名 */
    private Integer status;

    private LocalDateTime joinedAt;
}
