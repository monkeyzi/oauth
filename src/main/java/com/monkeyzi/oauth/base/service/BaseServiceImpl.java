package com.monkeyzi.oauth.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/13 18:17
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    protected Mapper<T> mapper;

    /**
     * Gets mapper.
     *
     * @return the mapper
     */
    public Mapper<T> getMapper() {
        return mapper;
    }


    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }
}
