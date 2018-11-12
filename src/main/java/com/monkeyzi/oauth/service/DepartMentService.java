package com.monkeyzi.oauth.service;

import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.Department;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.dept.DeptDto;
import com.monkeyzi.oauth.entity.dto.tree.TreeDto;

import java.util.List;

public interface DepartMentService extends BaseService<Department> {
    /**
     * 新增部门
     * @param deptDto
     * @param loginAuthUser
     * @return
     */
    int addDept(DeptDto deptDto, LoginAuthDto loginAuthUser);

    /**
     * 修改部门
     * @param deptDto
     * @param loginAuthUser
     * @return
     */
    int editDept(DeptDto deptDto, LoginAuthDto loginAuthUser);

    /**
     * 删除部门
     * @param ids
     */
    void deleteDept(List<String> ids);

    /**
     * 查询部门树
     * @return
     */
    List<TreeDto> queryDept();

    /**
     * 查询部门的详情
     * @param id
     * @return
     */
    Department queryDeptDetail(String id);
}
