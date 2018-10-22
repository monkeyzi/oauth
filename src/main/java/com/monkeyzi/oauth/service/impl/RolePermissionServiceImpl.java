package com.monkeyzi.oauth.service.impl;

import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.Role;
import com.monkeyzi.oauth.entity.domain.RolePermission;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.permission.BindPermissionDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.mapper.RoleMapper;
import com.monkeyzi.oauth.mapper.RolePermissionMapper;
import com.monkeyzi.oauth.mapper.UserRoleMapper;
import com.monkeyzi.oauth.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 高yg
 * @date: 2018/10/20 19:00
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements RolePermissionService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public void bindRolePermissions(BindPermissionDto bindPermissionDto, LoginAuthDto loginAuthUser) {
        //非管理员账户不能操作管理员角色
        if (!loginAuthUser.getId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)
                &&bindPermissionDto.getRoleId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_ROEL_ID)){
            log.error("不能操作管理员角色 roleId={}",bindPermissionDto.getRoleId());
            throw  new BusinessException(ErrorCodeEnum.RS304);
        }
        //查询操作的角色存在不存在
        Role  role=roleMapper.selectByPrimaryKey(bindPermissionDto.getRoleId());
        if (role==null){
            log.error("角色不存在 roleId={}",bindPermissionDto.getRoleId());
            throw new BusinessException(ErrorCodeEnum.RS301,bindPermissionDto.getRoleId());
        }
        //先删除已经绑定的关系
        RolePermission rolePermission=new RolePermission();
        rolePermission.setRoleId(bindPermissionDto.getRoleId());
        rolePermissionMapper.delete(rolePermission);

        //绑定新的关系
        bindPermissionDto.getPermissions().forEach(a->{
            rolePermission.setPermissionId(a);
            rolePermission.setUpdateInfo(loginAuthUser);
            rolePermissionMapper.insertSelective(rolePermission);
        });

    }
}
