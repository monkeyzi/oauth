package com.monkeyzi.oauth.service;

import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.Role;
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
}
