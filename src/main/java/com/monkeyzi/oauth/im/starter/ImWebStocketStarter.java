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
    @Autowired
    private ImServerConfig imServerConfig;

    public ImWebStocketStarter(int port, ImMsgHandler imMsgHandler) throws IOException {
        wsServerStarter=new WsServerStarter(port,imMsgHandler);
        serverGroupContext = wsServerStarter.getServerGroupContext();
        log.info("name===={}",imServerConfig.getMonkeyIm().getBindPort());
        serverGroupContext.setName(imServerConfig.getMonkeyIm().getProtocolName());
        serverGroupContext.setServerAioListener(ImServerAioListener.m);
        //设置ip监控
        serverGroupContext.setIpStatListener(ImIpStatListener.m);
        //设置ip监控时间段
        serverGroupContext.ipStats.addDurations(IpStatDuration.IPSTAT_DURATIONS);
        //设置心跳时长
        serverGroupContext.setHeartbeatTimeout(imServerConfig.getMonkeyIm().getHeartBeatTimeout());
        //初始化消息类型

    }

   /* *//**
     * 启动t-io
     * @throws Exception
     *//*
    public static void start() throws Exception{

        log.info("启动了---------------------");
        ImWebStocketStarter starter=new ImWebStocketStarter(ImServerConfig.bindPort,ImMsgHandler.m);
        starter.getWsServerStarter().start();
    }*/

    public WsServerStarter getWsServerStarter() {
        return wsServerStarter;
    }


    public ServerGroupContext getServerGroupContext() {
        return serverGroupContext;
    }

}
