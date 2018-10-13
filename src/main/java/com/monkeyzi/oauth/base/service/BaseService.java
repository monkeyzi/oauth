package com.monkeyzi.oauth.base.service;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/13 18:15
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface BaseService<T> {

    /**
     * 查询全部结果，使用select(null)也能达到同样的结果
     * @return
     */
    List<T> selectAll();

}
