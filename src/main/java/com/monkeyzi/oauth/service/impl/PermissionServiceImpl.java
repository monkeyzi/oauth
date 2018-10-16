package com.monkeyzi.oauth.service.impl;

import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.Permission;
import com.monkeyzi.oauth.enums.SysEnum;
import com.monkeyzi.oauth.mapper.PermissionMapper;
import com.monkeyzi.oauth.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/13 18:29
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<Permission> selectPermissionByType(Integer type) {
        Permission permission=new Permission();
        permission.setStatus(0);
        permission.setType(type);
        return permissionMapper.select(permission);
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public Permission matchUrl(String requestUrl) {
        List<Permission> permissionList=this.selectPermissionByType(SysEnum.PERMISSION_CODE.code);
        for (Permission permission:permissionList){
            String url=permission.getPath();
            if (StringUtils.isEmpty(url)){
                continue;
            }
            if (antPathMatcher.match(requestUrl,url)){
                return permission;
            }
        }
        return null;
    }
}
