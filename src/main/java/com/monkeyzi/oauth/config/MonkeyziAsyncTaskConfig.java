package com.monkeyzi.oauth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author: 高yg
 * @date: 2018/10/14 20:23
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Configuration
@EnableAsync
@EnableScheduling
@Slf4j
public class MonkeyziAsyncTaskConfig implements AsyncConfigurer {

    @Autowired
    private MonkeyziProperties monkeyziProperties;


    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        log.info("task-----------Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(monkeyziProperties.getTask().getCorePoolSize());
        executor.setMaxPoolSize(monkeyziProperties.getTask().getMaxPoolSize());
        executor.setQueueCapacity(monkeyziProperties.getTask().getQueueCapacity());
        executor.setKeepAliveSeconds(monkeyziProperties.getTask().getKeepAliveSeconds());
        executor.setThreadNamePrefix(monkeyziProperties.getTask().getThreadNamePrefix());
        return new ExceptionHandlingAsyncTaskExecutor(executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
