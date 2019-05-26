package com.monkeyzi.oauth.im.processor;

import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;

/**
 * @author 高艳国
 * @date 2018/12/4 9:12
 * @description 消息总处理接口
 **/
public interface ImMsgProcessor {

    WsResponse process(WsRequest packet, ChannelContext channelContext) throws Exception;

}
