package com.monkeyzi.oauth.im.handler;

import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

@Slf4j
public class ImMsgHandler implements IWsMsgHandler {

    public static final ImMsgHandler m=new ImMsgHandler();
    private ImMsgHandler(){

    }

    /**
     * 握手的时候走这个方法，可以获取request,cookie等信息
     * 返回null 会断开链接
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        log.info("握手了-----------");
        String clientIp=httpRequest.getClientIp();
        log.info("握手获取的ip是 ip={}",clientIp);
        return httpResponse;
    }

    /**
     * 握手完成
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @throws Exception
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        log.info("握手结束了--------");
    }

    /**
     * 字节消息会走这个方法
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        log.info("字符消息 byte={}",bytes);
        return null;
    }

    /**
     * 客户端发送 colse标志的时候走这个方法
     * @param wsRequest
     * @param bytes
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 字符消息走这个方法
     * @param wsRequest
     * @param s
     * @param channelContext
     * @return
     * @throws Exception
     */
    @Override
    public Object onText(WsRequest wsRequest, String s, ChannelContext channelContext) throws Exception {
        log.info("字符消息 s={}",s);
        return null;
    }
}
