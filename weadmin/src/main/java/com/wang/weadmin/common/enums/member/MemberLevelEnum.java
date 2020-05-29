package com.wang.weadmin.common.enums.member;

/**
 * @description: 会员等级枚举类
 * @author: WANG Y.G.
 * @time: 2020/01/28 16:42
 * @version: V1.0
 */
public enum MemberLevelEnum {
    /**
     * 普通会员
     */
    NORMAL(0),
    /**
     * VIP
     */
    VIP(1),
    /**
     * SVIP
     */
    SVIP(2);

    private final Integer level;

    MemberLevelEnum(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }
}
