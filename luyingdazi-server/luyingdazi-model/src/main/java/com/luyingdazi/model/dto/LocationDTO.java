package com.luyingdazi.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 位置上报参数
 *
 * @author luyingdazi
 */
@Data
public class LocationDTO {

    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;

    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

    /** 城市名（可选，前端通过逆地理编码获得） */
    private String city;
}
