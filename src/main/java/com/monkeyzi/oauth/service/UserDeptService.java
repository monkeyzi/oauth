package com.monkeyzi.oauth.service;

import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.UserDepartment;
import com.monkeyzi.oauth.mapper.UserDepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: 高yg
 * @date: 2018/10/18 22:34
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface UserDeptService extends BaseService<UserDepartment> {

    /**
     * 根据userId更新部门用户关系
     * @param userDepartment
     * @return
     */
   int updateByUserId(UserDepartment userDepartment);

}
