package com.monkeyzi.oauth.service;

import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.Permission;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.permission.PermissionDto;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/13 18:28
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface PermissionService extends BaseService<Permission> {

    /**
     * 根据权限类型查询权限信息
     * @param type
     * @return
     */
    List<Permission> selectPermissionByType(Integer type);

    /**
     * 根据请求url匹配权限
     * @param requestUrl
     * @return
     */
    Permission  matchUrl(String requestUrl);

    /**
     * 根据角色Id获取菜单/权限树
     * @param roleId
     * @param loginAuthDto
     * @return
     */
    List<PermissionDto> getAllPermissionListByRoleId(String roleId, LoginAuthDto loginAuthDto);

    /**
     * 查询系统所有的菜单/权限递归成树
     * @return
     */
    List<PermissionDto> getAllPermissionList();

    /**
     * 添加/修改权限
     * @param permissionDto
     * @param loginAuthUser
     * @return
     */
    int addPermission(PermissionDto permissionDto, LoginAuthDto loginAuthUser);

    /**
     * 删除菜单/权限
     * @param ids
     */
    void deletePermission(List<String> ids);
}
