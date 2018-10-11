package com.monkeyzi.oauth.security.oauth;

import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.utils.ResponseJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: 高yg
 * @date: 2018/10/10 22:54
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description: 未认证  响应给客户端统一json
 */
@Slf4j
public class AuthExceptionEntryPoint  implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error("没有登录,请登录认证");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,ErrorCodeEnum.GL401.getCode(),ErrorCodeEnum.GL401.getMsg(),null));
    }


}
