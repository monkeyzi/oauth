package com.monkeyzi.oauth.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: 高yg
 * @date: 2018/10/18 21:31
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description: 解决访问 swagger-ui.html 404的问题
 */
@EnableWebMvc
@Configuration
public class WebmvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/");
    }
}
