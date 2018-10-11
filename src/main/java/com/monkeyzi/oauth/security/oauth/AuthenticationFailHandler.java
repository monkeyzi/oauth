package com.monkeyzi.oauth.security.oauth;

import com.monkeyzi.oauth.utils.ResponseJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器，在这个方法中记录登录的失败原因，失败次数，多次失败禁用账户等操作
 */
@Slf4j
@Component
public class AuthenticationFailHandler  extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("登录失败");
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException){
            String userName=request.getParameter("username");
            System.out.println(userName);
            ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,"用户名或者密码错误",null));
        }else  if (e instanceof DisabledException){
            //账户被禁用
            ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,"账户被禁用",null));
        }else {
            //其他内部异常
            ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,"登录失败,内部异常",null));
        }
    }
}
