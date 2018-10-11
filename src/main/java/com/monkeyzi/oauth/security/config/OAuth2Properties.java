package com.monkeyzi.oauth.security.config;

import lombok.Data;

@Data
public class OAuth2Properties {

    /**
     * 使用jwt时为token签名的秘钥
     */
    private String jwtSigningKey = "monkeyzi";
    /**
     * 客户端配置
     */
    private OAuth2ClientProperties[] clients = {};
}
