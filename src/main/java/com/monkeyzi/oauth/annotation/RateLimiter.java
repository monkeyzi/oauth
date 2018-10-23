package com.monkeyzi.oauth.annotation;

import java.lang.annotation.*;

/**
 * @author 高艳国
 * @date 2018/10/23 17:17
 * @description 限流注解
 **/
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {

    int limit() default 5;

    /**
     * 默认一秒没最多5个请求
     * @return
     */
    int timeout() default 1000;

}
