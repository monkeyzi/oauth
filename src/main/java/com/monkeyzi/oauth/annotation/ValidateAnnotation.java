package com.monkeyzi.oauth.annotation;

import java.lang.annotation.*;

/**
 * 参数验证注解
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateAnnotation {

    boolean isValidate() default true;
}
