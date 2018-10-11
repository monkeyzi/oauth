package com.monkeyzi.oauth.security.oauth;

import com.monkeyzi.oauth.security.config.OAuth2ClientProperties;
import com.monkeyzi.oauth.security.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 客户端属性配置
 */
@Component
@Slf4j
public class RestClientDetailService implements ClientDetailsService {

    private ClientDetailsService clientDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @PostConstruct
    public void init(){
        InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
        try {
            if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())){
                 for (OAuth2ClientProperties client:securityProperties.getOauth2().getClients()){
                     builder.withClient(client.getClientId())
                             .secret(passwordEncoder.encode(client.getClientSecret()))
                             .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                             .accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
                             .refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds())
                             .scopes(client.getScope());
                 }
            }
            clientDetailsService=builder.build();
        }catch (Exception e){
            log.error("client init error={}",e.getMessage(),e);
        }
    }
    @Override
    public ClientDetails loadClientByClientId(String cilentId) throws ClientRegistrationException {
        return clientDetailsService.loadClientByClientId(cilentId);
    }
}
