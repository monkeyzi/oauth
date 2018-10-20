package com.monkeyzi.oauth.mapper;

import com.monkeyzi.oauth.base.mybatis.MyMapper;
import com.monkeyzi.oauth.entity.domain.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DepartMentMapper  extends MyMapper<Department> {

    /**
     * 根据用户ID查询用户的部门
     * @param userId
     * @return
     */
    List<Department> selectDeptListByUserId(String userId);
}
