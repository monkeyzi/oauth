package com.monkeyzi.oauth.security;

import com.google.common.collect.Lists;
import com.monkeyzi.oauth.entity.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserDetailServiceImpl  implements UserDetailsService {

    private static final List<User> users=Lists.newArrayList();



    static {
        User user=new User();
        user.setUsername("user1");
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        user.setNickName("test1");
        users.add(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = users.stream()
                .filter((u) -> u.getUsername().equals(s))
                .findFirst();
        if (!user.isPresent())
            throw new UsernameNotFoundException("there's no user founded!");
        else  return new SecurityUserDetails(user.get());
    }
}
