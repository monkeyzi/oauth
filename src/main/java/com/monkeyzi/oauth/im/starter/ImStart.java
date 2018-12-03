package com.monkeyzi.oauth.im.starter;

import com.monkeyzi.oauth.im.config.ImServerConfig;
import com.monkeyzi.oauth.im.handler.ImMsgHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ImStart {


    @Autowired
    private ImServerConfig imServerConfig;



    @Bean
    ImWebStocketStarter starter() throws Exception {
        log.info("pppp=========="+imServerConfig.getIm().getBindPort());
        log.info("启动了---------------------");
        ImWebStocketStarter starter=new ImWebStocketStarter(imServerConfig);
        starter.getWsServerStarter().start();
        return starter;
    }
}
