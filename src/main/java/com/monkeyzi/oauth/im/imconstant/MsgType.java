package com.monkeyzi.oauth.im.imconstant;

/**
 * 消息推送类型
 */
public interface MsgType {
    /**
     * 单聊
     */
    byte CLIENT_TO_CLIENT=0;
    /**
     * 群聊
     */
    byte CLIENT_TO_GROUP=1;
    /**
     * 服务端向客户端推送消息
     */
    byte SERVER_TO_CLIENT_NOTICE=2;


}
