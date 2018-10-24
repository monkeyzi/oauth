package com.monkeyzi.oauth.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Md5Util {
    /**
     * 加密
     * @param password  明文
     * @return 加密后的密文
     */
    public static String encrypt(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    /**
     * 密码是否匹配
     * @param rawPassword 明文
     * @param encodedPassword 密文
     * @return
     */
    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    public static void main(String[] args) {
        String md=encrypt("1223gao");
        System.out.println(md);
    }

}
