package com.luyingdazi.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 活动实体（组队露营）
 *
 * @author luyingdazi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_activity")
public class Activity extends BaseEntity {

    /** 发起人ID */
    private Long userId;

    /** 标题 */
    private String title;

    /** 描述 */
    private String description;

    /** 封面图 */
    private String coverImage;

    /** 地点名称 */
    private String locationName;

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

    /** 开始时间 */
    private LocalDateTime startTime;

    /** 结束时间 */
    private LocalDateTime endTime;

    /** 最大人数（0不限） */
    private Integer maxMembers;

    /** 当前人数 */
    private Integer currentMembers;

    /** 费用说明 */
    private String feeDesc;

    /** 参与要求 */
    private String requirement;

    /** 状态：0取消 1报名中 2已满 3进行中 4已结束 */
    private Integer status;
}
