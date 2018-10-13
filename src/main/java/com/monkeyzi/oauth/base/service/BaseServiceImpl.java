package com.monkeyzi.oauth.base.service;

import com.monkeyzi.oauth.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
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
@Slf4j
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


    @Override
    public List<T> select(T record) {
        return mapper.select(record);
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public T selectOne(T record) {
        return mapper.selectOne(record);
    }


    @Override
    public int selectCount(T record) {
        return mapper.selectCount(record);
    }

    @Override
    public int saveSelective(T record) {
        return mapper.insertSelective(record);
    }

    @Override
    public int save(T record) {
        return mapper.insert(record);
    }

    @Override
    public int batchSave(List<T> list) {
        int result=0;
        for (T a:list){
            int count=mapper.insertSelective(a);
            result+=count;
        }
        return result;
    }


    @Override
    public int update(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(T record) {
        return mapper.delete(record);
    }

    @Override
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }


    @Override
    public int batchDelete(List<T> list) {
        int result = 0;
        for (T record : list) {
            int count = mapper.delete(record);
            if (count < 1) {
                log.error("删除数据失败");
                throw new BusinessException("删除数据失败!");
            }
            result += count;
        }
        return result;
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int deleteByExample(Object example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int updateByExample(T record, Object example) {
        return mapper.updateByExampleSelective(record,example);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
        return mapper.selectByExampleAndRowBounds(example,rowBounds);
    }

    @Override
    public List<T> selectByRowBounds(T record, RowBounds rowBounds) {
        return mapper.selectByRowBounds(record,rowBounds);
    }
}
