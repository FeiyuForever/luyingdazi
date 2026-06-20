package com.luyingdazi.model.query;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 附近的人查询参数
 *
 * @author luyingdazi
 */
@Data
public class NearbyQuery {

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

    /** 搜索半径（km），默认10 */
    private Double radiusKm = 10.0;

    /** 返回数量，默认20 */
    private Integer count = 20;

    /** 性别筛选（可选）：1男 2女 */
    private Integer gender;

    /** 标签筛选（可选） */
    private String tag;
}
