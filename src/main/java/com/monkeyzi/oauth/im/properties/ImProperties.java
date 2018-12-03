package com.monkeyzi.oauth.im.properties;

import lombok.Data;

@Data
public class ImProperties {

    public String  charset;

    public int bindPort;

    public String  bindIp;

    public int heartBeatTimeout;

    public String protocolName;


}
