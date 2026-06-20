package com.luyingdazi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author luyingdazi
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {

    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private final int code;
    private final String desc;

    public static GenderEnum of(int code) {
        for (GenderEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
