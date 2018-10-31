package com.monkeyzi.oauth.interceptor;

import com.monkeyzi.oauth.annotation.RateLimiter;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.common.RedisRaterLimiter;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class LimitRaterInterceptor extends HandlerInterceptorAdapter {

    @Value("${monkeyzi.rateLimit.enable}")
    private boolean rateLimitEnable;

    @Value("${monkeyzi.rateLimit.limit}")
    private Integer limit;

    @Value("${monkeyzi.rateLimit.timeout}")
    private Integer timeout;

    @Autowired
    private RedisRaterLimiter redisRaterLimiter;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入限流拦截器了");
        String ipAddr=RequestUtils.getRemoteAddr(request);
        //ip限流  一秒内限制5个请求
        String tokenIp=redisRaterLimiter.acquireTokenFromBucket(ipAddr,2,1000);
        if (StringUtils.isBlank(tokenIp)){
            log.error("该用户请求太快了 ip={}",ipAddr);
            throw new BusinessException(ErrorCodeEnum.GL10002);
        }

        //全局限流
        if (rateLimitEnable){
            String tokenGl=redisRaterLimiter.acquireTokenFromBucket(GlobalConstant.Sys.SYS_RATER_LIMIT_KEY,limit,timeout);
            if (StringUtils.isBlank(tokenGl)){
                throw new BusinessException(ErrorCodeEnum.GL10003);
            }
        }

        //获取注解限流
        HandlerMethod handlerMethod;
        if (handler instanceof HandlerMethod){
            handlerMethod= (HandlerMethod) handler;
            Method method=handlerMethod.getMethod();
            RateLimiter rateLimiter=method.getAnnotation(RateLimiter.class);
            if (rateLimiter!=null){
                int limit=rateLimiter.limit();
                int timeout=rateLimiter.timeout();
                String tokenMethod=redisRaterLimiter.acquireTokenFromBucket(method.getName(),limit,timeout);
                if (StringUtils.isBlank(tokenMethod)){
                    throw new BusinessException(ErrorCodeEnum.GL10003);
                }
            }
        }

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
