package com.monkeyzi.oauth.im.listener;

import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.websocket.server.WsServerAioListener;

/**
 * 服务层面进行监听
 */
@Slf4j
public class ImServerAioListener extends WsServerAioListener {

      public static final ImServerAioListener m=new ImServerAioListener();

      private ImServerAioListener(){

      }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
          log.info("-------------serverListener onAfterConnected--------------------");
          super.onAfterConnected(channelContext, isConnected, isReconnect);
    }


    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {
        log.info("-------------serverListener onAfterSent--------------------");
        super.onAfterSent(channelContext, packet, isSentSuccess);
    }

    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
          log.info("-------------serverListener onBeforeClose--------------------");
          super.onBeforeClose(channelContext, throwable, remark, isRemove);
    }


    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {
        log.info("-------------serverListener onAfterDecoded--------------------");
        super.onAfterDecoded(channelContext, packet, packetSize);
    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, long cost) throws Exception {
        log.info("-------------serverListener onAfterHandled--------------------");
        super.onAfterHandled(channelContext, packet, cost);
    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes) throws Exception {
        log.info("-------------serverListener onAfterReceivedBytes--------------------");
        super.onAfterReceivedBytes(channelContext, receivedBytes);
    }
}
