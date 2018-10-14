package com.monkeyzi.oauth.config;

import lombok.Data;

/**
 * @author: 高yg
 * @date: 2018/10/14 20:21
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
public class TaskProperties {

    private int corePoolSize = 5;

    private int maxPoolSize = 50;

    private int queueCapacity = 10000;

    private int keepAliveSeconds = 3000;

    private String threadNamePrefix = "monkeyzi-task-executor-";
}
