package com.monkeyzi.oauth.interceptor;

import com.monkeyzi.oauth.security.config.IgnoredUrlsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器
 */
@Slf4j
@Configuration
public class InterceptorConfiguration  implements WebMvcConfigurer {

    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private LimitRaterInterceptor limitRaterInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册token拦截器
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(ignoredUrlsProperties.getUrls());
        // 限流拦截器
        registry.addInterceptor(limitRaterInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(ignoredUrlsProperties.getUrls());

    }
}
