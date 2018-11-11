package com.monkeyzi.oauth.interceptor;

import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.UserToken;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.UserTokenDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.utils.PublicUtil;
import com.monkeyzi.oauth.utils.RedisKeyUtil;
import com.monkeyzi.oauth.utils.ThreadLocalMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.helpers.ThreadLocalMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token拦截器
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Value("${monkeyzi.security.oauth2.jwtSigningKey}")
    private String jwtSigningKey;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("进入拦截器了");
        String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), "Bearer ");
        if (StringUtils.isEmpty(token)){
            log.error("token不能为空,请登录获取token");
            throw  new BusinessException(ErrorCodeEnum.GL401);
        }
        //从redis中获取token

       LoginAuthDto loginAuthDto=(UserTokenDto) redisTemplate.opsForValue().get(RedisKeyUtil.getAccessTokenKey(token));
        //如果能取到就放行
        if (PublicUtil.isEmpty(loginAuthDto)){
            log.error("根据token已失效或者未登录");
            throw  new BusinessException(ErrorCodeEnum.GL401);
        }
        //将用户的信息放入threadLocalMap
        ThreadLocalMapUtils.put(GlobalConstant.Sys.CURRENT_AUTH_USER,loginAuthDto);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
