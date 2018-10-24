package com.monkeyzi.oauth.security;

import com.google.common.collect.Lists;
import com.monkeyzi.oauth.entity.domain.User;
import com.monkeyzi.oauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserDetailServiceImpl  implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Resource
    private StringRedisTemplate redisTemplate;

    private static final List<User> users=Lists.newArrayList();





    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userService.findUserByUserName(s));
        if (!user.isPresent())
            throw new UsernameNotFoundException("用户名不存在");
        else  return new SecurityUserDetails(user.get());
    }
}
