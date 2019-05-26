package com.monkeyzi.oauth.security.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限处理---没有权限的时候
 */
@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.error("对不起,该用户的请求没有权限");
        Map<String, Object> result = new HashMap<>(4);
        result.put("code", ErrorCodeEnum.GL403.getCode());
        result.put("msg", ErrorCodeEnum.GL403.getMsg());
        result.put("success",false);
        result.put("data",null);
        String json = objectMapper.writeValueAsString(result);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
}
