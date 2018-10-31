package com.monkeyzi.oauth.security;

import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.User;
import com.monkeyzi.oauth.exception.LoginFailLimitException;
import com.monkeyzi.oauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class UserDetailServiceImpl  implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("用户名密码");
        String failKey=GlobalConstant.Sys.SYS_LOGIN_FAIL_LIMIT+":"+s;
        String value=redisTemplate.opsForValue().get(failKey);
        Long timeRest = redisTemplate.getExpire(failKey, TimeUnit.MINUTES);
        if (StringUtils.isNotBlank(value)){
            throw new LoginFailLimitException("登录错误次数超过限制，请"+timeRest+"分钟后再试");
        }
        Optional<User> user = Optional.ofNullable(userService.findUserByUserName(s));
        if (!user.isPresent()){
            throw new UsernameNotFoundException("用户名不存在");
        }else {
            return new SecurityUserDetails(user.get());
        }
    }
}
