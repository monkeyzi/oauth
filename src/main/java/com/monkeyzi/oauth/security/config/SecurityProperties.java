package com.monkeyzi.oauth.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "monkeyzi.security")
@Getter
@Setter
@Component
public class SecurityProperties {
    /**
     * OAuth2认证服务器配置
     */
    private OAuth2Properties oauth2 = new OAuth2Properties();
    /**
     * 浏览器配置
     */
    private BrowerProperties brower=new BrowerProperties();
}
