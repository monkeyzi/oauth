package com.monkeyzi.oauth.security.oauth;

import com.google.common.collect.Maps;
import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.entity.domain.User;
import com.monkeyzi.oauth.enums.LogTypeEnum;
import com.monkeyzi.oauth.service.UserService;
import com.monkeyzi.oauth.utils.Md5Util;
import com.monkeyzi.oauth.utils.RequestUtils;
import com.monkeyzi.oauth.utils.ResponseJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 认证成功处理类
 */
@Slf4j
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String BEARER_TOKEN_TYPE = "Basic ";

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;
    @Autowired
    private UserService userService;

    @Override
    @LogAnnotation(description = "登录日志",logType = LogTypeEnum.LOGIN_LOG)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        User user= (User) authentication.getPrincipal();
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        log.info("登陆成功了,获取的用户名是 username={}",username);
        List<GrantedAuthority> list = (List<GrantedAuthority>)
                ((UserDetails)authentication.getPrincipal()).getAuthorities();
        log.info("权限 ={}",list);
        //获取请求头中的client信息
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(BEARER_TOKEN_TYPE)) {
            log.error("请求头中无client信息");
            //throw new UnapprovedClientAuthenticationException("请求头中没有client信息");
            ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,
                    "请求头中没有client信息",null));
            return;
        }
        //解析client信息
        String[] tokens=RequestUtils.extractAndDecodeHeader(header);
        assert tokens.length == 2;
        String clientId = tokens[0];
        String clientSecret = tokens[1];
        ClientDetails clientDetails=clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails==null){
            log.error("找不到对应的client信息 clientId={}",clientId);
            //throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
            ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,
                    "clientId对应的信息不存在",null));
            return;
        }else  if (!Md5Util.matches(clientSecret,clientDetails.getClientSecret())){
            log.error("clientSecret不匹配 clientId={},clientSecret={}",clientId,clientSecret);
            //throw new UnapprovedClientAuthenticationException("clientSecret信息不匹配");
            ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,
                    "clientSecret信息不匹配",null));
            return;
        }

        // 密码授权 模式, 组建 authentication
        TokenRequest tokenRequest=new TokenRequest(Maps.newHashMap(),clientId, clientDetails.getScope(), "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        // 处理登录信息---记录token
        userService.handlerLoginData(token,user,request);
        //登陆成功,返回token给客户端
        ResponseJsonUtil.out(response,ResponseJsonUtil.map(true,200,"登陆成功",token));
    }
}
