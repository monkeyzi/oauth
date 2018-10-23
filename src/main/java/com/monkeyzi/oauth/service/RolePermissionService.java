package com.monkeyzi.oauth.service;

import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.RolePermission;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.permission.BindPermissionDto;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/20 19:00
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface RolePermissionService  extends BaseService<RolePermission> {
    /**
     * 给角色分配权限
     * @param bindPermissionDto
     * @param loginAuthUser
     */
    void bindRolePermissions(BindPermissionDto bindPermissionDto, LoginAuthDto loginAuthUser);

    /**
     * 查询角色已经绑定的角色权限关系列表
     * @param roleId
     * @return
     */
    List<RolePermission> selectRolePermissionsByRoleId(String roleId);
}
