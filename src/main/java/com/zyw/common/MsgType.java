package com.zyw.common;

/**
 * @author wangminggen
 */
public enum MsgType {
    LOGIN(1),
    LOGIN_SUCCESS(2),
    TEXT(3);
    private final int type;

    MsgType(int type) {
        this.type = type;
    }
}
