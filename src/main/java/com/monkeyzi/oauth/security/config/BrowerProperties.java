package com.monkeyzi.oauth.security.config;

import lombok.Data;

/**
 * @author: 高yg
 * @date: 2018/10/14 22:08
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
public class BrowerProperties {

    private int rememberMeSeconds = 60*60*24*7;
}
