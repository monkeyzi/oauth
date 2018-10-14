package com.monkeyzi.oauth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 高yg
 * @date: 2018/10/14 20:20
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@ConfigurationProperties(prefix = "monkeyzi.task")
@Getter
@Setter
@Component
public class MonkeyziProperties {
    /**
     * task异步任务
     */
   private TaskProperties task=new TaskProperties();
}
