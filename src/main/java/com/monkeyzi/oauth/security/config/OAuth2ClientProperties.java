package com.monkeyzi.oauth.security.config;

import lombok.Data;

@Data
public class OAuth2ClientProperties {
    /**
     * 第三方应用appId
     */
    private String clientId;
    /**
     * 第三方应用appSecret
     */
    private String clientSecret;
    /**
     * 针对此应用发出的token的有效时间
     */
    private int accessTokenValidateSeconds = 7200;
    /**
     * 针对此应用的refreshToken的有效期
     */
    private int refreshTokenValiditySeconds = 2592000;
    /**
     * 作用范围
     */
    private String scope;
}
