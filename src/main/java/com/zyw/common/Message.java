package com.zyw.common;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Message implements java.io.Serializable {
    /**
     * 消息类型
     */
    private MsgType msgType;
    /**
     * 发送者
     */
    private int from;
    /**
     * 发送者昵称
     */
    private String nickName;

    /**
     * 内容
     */
    private String conn;

    /**
     * 发送时间
     */
    private long sendTime;
}
