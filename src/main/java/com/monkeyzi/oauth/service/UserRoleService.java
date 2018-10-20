package com.monkeyzi.oauth.service;

import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.UserRole;

/**
 * @author: 高yg
 * @date: 2018/10/20 18:54
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public interface UserRoleService  extends BaseService<UserRole> {

    /**
     * 删除用户角色关系
     * @param userId
     */
    void deleteUserRoleByUserId(String userId);
}
