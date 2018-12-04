package com.monkeyzi.oauth.im.processor;

import com.monkeyzi.oauth.im.packet.ImBaseBody;
import com.monkeyzi.oauth.im.util.ByteUtil;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
/**
 * @author 高艳国
 * @date 2018/12/4 10:08
 * @description 抽象的消息处理器
 **/
public abstract class ImAbsMsgProcessor<T extends ImBaseBody> implements ImMsgProcessor  {

    @Override
    public WsResponse process(WsRequest packet, ChannelContext channelContext) throws Exception {
        Class<T> clazz=getBodyClass();
        T body=null;
        if (packet.getBody()!=null){
           String json=ByteUtil.toText(packet.getBody());
           body=Json.toBean(json,clazz);
        }
        return process(packet,body,channelContext);
    }

    public abstract WsResponse process(WsRequest packet, T body, ChannelContext channelContext)throws Exception;

    public abstract Class<T> getBodyClass();


}
