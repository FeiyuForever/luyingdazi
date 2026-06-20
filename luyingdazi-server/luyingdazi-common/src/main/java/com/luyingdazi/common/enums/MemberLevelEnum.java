package com.luyingdazi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员等级枚举
 *
 * @author luyingdazi
 */
@Getter
@AllArgsConstructor
public enum MemberLevelEnum {

    FREE(0, "普通用户"),
    MONTHLY(1, "月度会员"),
    QUARTERLY(2, "季度会员"),
    YEARLY(3, "年度会员");

    private final int code;
    private final String desc;

    public static MemberLevelEnum of(int code) {
        for (MemberLevelEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return FREE;
    }

    /** 是否是付费会员 */
    public boolean isPaid() {
        return this != FREE;
    }
}
