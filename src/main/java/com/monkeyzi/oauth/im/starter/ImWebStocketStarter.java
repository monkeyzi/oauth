package com.monkeyzi.oauth.im.starter;

import com.monkeyzi.oauth.im.config.ImServerConfig;
import com.monkeyzi.oauth.im.handler.ImMsgHandler;
import com.monkeyzi.oauth.im.imconstant.IpStatDuration;
import com.monkeyzi.oauth.im.listener.ImIpStatListener;
import com.monkeyzi.oauth.im.listener.ImServerAioListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerStarter;

import java.io.IOException;
@Slf4j
public class ImWebStocketStarter {



    private WsServerStarter    wsServerStarter;
    private ServerGroupContext serverGroupContext;



    public ImWebStocketStarter(ImServerConfig imServerConfig) throws IOException {
        wsServerStarter=new WsServerStarter(imServerConfig.getIm().getBindPort(),ImMsgHandler.m);
        serverGroupContext = wsServerStarter.getServerGroupContext();
        log.info("name===={}",imServerConfig.getIm().getBindPort());
        serverGroupContext.setName(imServerConfig.getIm().getProtocolName());
        serverGroupContext.setServerAioListener(ImServerAioListener.m);
        //设置ip监控
        serverGroupContext.setIpStatListener(ImIpStatListener.m);
        //设置ip监控时间段
        serverGroupContext.ipStats.addDurations(IpStatDuration.IPSTAT_DURATIONS);
        //设置心跳时长
        serverGroupContext.setHeartbeatTimeout(imServerConfig.getIm().getHeartBeatTimeout());
        //初始化消息类型

    }


    public WsServerStarter getWsServerStarter() {
        return wsServerStarter;
    }


    public ServerGroupContext getServerGroupContext() {
        return serverGroupContext;
    }

}
