package com.monkeyzi.oauth.im.config;

import lombok.Data;

@Data
public class ImProperties {

    private String  charset;

    private int bindPort;

    private String  bindIp;

    private int heartBeatTimeout;

    private String protocolName;


}
