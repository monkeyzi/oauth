package com.monkeyzi.oauth.service.impl;

import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.UserToken;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.mapper.UserTokenMapper;
import com.monkeyzi.oauth.security.config.OAuth2ClientProperties;
import com.monkeyzi.oauth.security.config.SecurityProperties;
import com.monkeyzi.oauth.service.CommonService;
import com.monkeyzi.oauth.service.UserTokenService;
import com.monkeyzi.oauth.utils.RedisKeyUtil;
import com.monkeyzi.oauth.utils.RequestUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserTokenServiceImpl  extends BaseServiceImpl<UserToken> implements UserTokenService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public int saveUserToken(OAuth2AccessToken token, LoginAuthDto loginAuthDto, HttpServletRequest request) {
        //获取浏览器信息
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-Agent"));
        //获取客户端操作系统
        final String os = userAgent.getOperatingSystem().getName();
        //获取客户端浏览器
        final String browser = userAgent.getBrowser().getName();

        String  ipAddr=RequestUtils.getRemoteAddr(request);

        String  location=commonService.getAddressLocationByIp(ipAddr);

        String access_token=token.getValue();
        String refresh_token=token.getRefreshToken().getValue();
        String tokenType=token.getTokenType();
        //获取到token的有效期
        OAuth2ClientProperties[] clients=securityProperties.getOauth2().getClients();
        int accessTokenValidateSeconds = clients[0].getAccessTokenValidateSeconds();
        int refreshTokenValiditySeconds = clients[0].getRefreshTokenValiditySeconds();
        UserToken userToken=new UserToken();
        userToken.setUpdateInfo(loginAuthDto);
        userToken.setLoginIp(ipAddr);
        userToken.setLoginLocation(location);
        userToken.setOs(os);
        userToken.setBrowser(browser);
        userToken.setLoginTime(new Date());
        userToken.setAccessToken(access_token);
        userToken.setRefreshToken(refresh_token);
        userToken.setTokenType(tokenType);
        userToken.setAccessTokenValidity(accessTokenValidateSeconds);
        userToken.setRefreshTokenValidity(refreshTokenValiditySeconds);
        userToken.setId(generateId());
        userToken.setUserId(loginAuthDto.getId());
        userToken.setUserName(loginAuthDto.getUserName());
        //保存用户token信息
        int result=userTokenMapper.insertSelective(userToken);
        //将token保存在redis中
        updateUserTokenToRedis(access_token,accessTokenValidateSeconds,userToken);
        return result;
    }

    /**
     * 将token存入redis中
     * @param accessToken
     * @param accessTokenValidateSeconds
     * @param userToken
     */
    private void updateUserTokenToRedis(String accessToken,int accessTokenValidateSeconds,UserToken userToken){
       redisTemplate.opsForValue().set(RedisKeyUtil.getAccessTokenKey(accessToken),userToken,
               accessTokenValidateSeconds,TimeUnit.SECONDS);
    }
}
