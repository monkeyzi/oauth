package com.monkeyzi.oauth.mapper;

import com.monkeyzi.oauth.base.mybatis.MyMapper;
import com.monkeyzi.oauth.entity.domain.UserDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author: 高yg
 * @date: 2018/10/18 22:33
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Mapper
@Component
public interface UserDepartmentMapper  extends MyMapper<UserDepartment> {
}
