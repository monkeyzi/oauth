package com.monkeyzi.oauth.service;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.Role;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.role.RoleDefaultDto;
import com.monkeyzi.oauth.entity.dto.role.RoleDto;
import com.monkeyzi.oauth.entity.dto.role.RoleQueryDto;
import com.monkeyzi.oauth.entity.dto.roleuser.BindRoleDto;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/20 18:58
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface RoleService  extends BaseService<Role> {

    /**
     * 获取所有的角色列表
     * @return
     */
    List<BindRoleDto> allBindRoleList();

    /**
     * 新增角色
     * @param roleDto
     * @param loginAuthDto
     * @return
     */
    int addRole(RoleDto roleDto, LoginAuthDto loginAuthDto);

    /**
     * 查询系统所有可用的角色
     * @return
     */
    List<Role> getAllRoleList();

    /**
     * 分页查询系统可用的角色
     * @return
     */
    PageInfo getAllRoleListWithPage(RoleQueryDto roleQueryDto);

    /**
     * 设置或者取消设置默认角色
     * @param roleDefaultDto
     * @param loginAuthUser
     * @return
     */
    int setDefaultRole(RoleDefaultDto roleDefaultDto, LoginAuthDto loginAuthUser);

    /**
     * 删除角色
     * @param ids
     * @param loginAuthUser
     * @return
     */
    void deleteRole(List<String> ids, LoginAuthDto loginAuthUser);

    /**
     * 禁用/启用角色
     * @param ids
     * @param loginAuthUser
     * @param status
     */
    void disableRole(List<String> ids, LoginAuthDto loginAuthUser,Integer status);
}
