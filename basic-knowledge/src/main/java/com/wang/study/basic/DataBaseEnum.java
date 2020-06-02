package com.wang.study.basic;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/02/04 14:19
 * @version: V1.0
 */
public enum  DataBaseEnum {
    /**
     * oracle 11g
     */
    ORACLE_11G("ORACLE", "11G"),
    /**
     * oracle 12c
     */
    ORACLE_12C("ORACLE", "12C");

    /**
     * 数据库类型
     */
    private String type;
    /**
     * 数据库版本
     */
    private String version;

    DataBaseEnum(String type, String version) {
        this.type = type;
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
