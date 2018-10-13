package com.monkeyzi.oauth.base.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author: 高yg
 * @date: 2018/10/13 18:14
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
