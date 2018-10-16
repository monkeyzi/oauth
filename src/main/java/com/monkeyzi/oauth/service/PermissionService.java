package com.monkeyzi.oauth.service;

import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.Permission;

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
}
