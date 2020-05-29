package com.wang.weadmin.common.enums.member;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/01/28 16:51
 * @version: V1.0
 */
public enum MemberSourceEnum {
    /**
     * PC
     */
    PC(0),
    /**
     * Android客户端
     */
    ANDROID(1),
    /**
     * iOS客户端
     */
    IOS(2),
    /**
     * 微信公众号
     */
    WECHAT(3);

    private final Integer source;

    MemberSourceEnum(Integer source) {
        this.source = source;
    }

    public Integer getSource() {
        return source;
    }
}
