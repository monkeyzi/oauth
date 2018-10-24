package com.monkeyzi.oauth.security.oauth;

import cn.hutool.core.util.StrUtil;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.exception.LoginFailLimitException;
import com.monkeyzi.oauth.utils.ResponseJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
import java.util.concurrent.TimeUnit;

/**
 * 认证失败处理器，在这个方法中记录登录的失败原因，失败次数，多次失败禁用账户等操作
 */
@Slf4j
@Component
public class AuthenticationFailHandler  extends SimpleUrlAuthenticationFailureHandler {

    @Value("${monkeyzi.loginLimit.loginTimeLimit}")
    private Integer loginTimeLimit;

    @Value("${monkeyzi.loginLimit.loginAfterTime}")
    private Integer loginAfterTime;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("登录失败");
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException){
            String userName=request.getParameter("username");
            //记录错误次数
            recordFailLogin(userName);
            String key=GlobalConstant.Sys.SYS_LOGIN_LIMIT_COUNT+":"+userName;
            String value=redisTemplate.opsForValue().get(key);
            if (StringUtils.isBlank(value)){
                value="0";
            }
            //获取已登录错误次数
            int loginFailTime = Integer.parseInt(value);
            //剩余可登录次数
            int restLoginTime = loginTimeLimit - loginFailTime;
            log.info("用户"+userName+"登录失败","还有"+restLoginTime+"次登录机会");
            if(restLoginTime<=3&&restLoginTime>0){
                ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,"用户名或者密码错误,还有"+restLoginTime+"次尝试机会",null));
            }else if (restLoginTime<=0){
                ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,"登录错误次数超过限制,请"+loginAfterTime+"分钟后重试",null));
            }else {
                ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,"用户名或者密码错误",null));
            }
        }else  if (e instanceof DisabledException){
            //账户被禁用
            ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,"账户被禁用",null));
        }else if (e instanceof LoginFailLimitException){
            ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,((LoginFailLimitException) e).getMsg(),null));
        } else {
            //其他内部异常
            ResponseJsonUtil.out(response,ResponseJsonUtil.map(false,500,"登录失败,内部异常",null));
        }
    }


    /**
     * 处理用户登录错误次数
     * @param userName
     * @return
     */
    private Boolean recordFailLogin(String userName){
        //错误次数
        String loginLimitCount=GlobalConstant.Sys.SYS_LOGIN_LIMIT_COUNT+":"+userName;
        //登录错误次数超限制
        String loginFailLimit=GlobalConstant.Sys.SYS_LOGIN_FAIL_LIMIT+":"+userName;
        String value = redisTemplate.opsForValue().get(loginLimitCount);
        if(StrUtil.isBlank(value)){
            value = "0";
        }
        //获取已登录错误次数
        int loginFailTime = Integer.parseInt(value) + 1;
        redisTemplate.opsForValue().set(loginLimitCount, String.valueOf(loginFailTime), loginAfterTime, TimeUnit.MINUTES);
        if(loginFailTime>=loginTimeLimit){
            redisTemplate.opsForValue().set(loginFailLimit, "fail", loginAfterTime, TimeUnit.MINUTES);
            return false;
        }
        return true;
    }
}
