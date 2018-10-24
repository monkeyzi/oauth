package com.monkeyzi.oauth.service.impl;

import com.google.common.base.Preconditions;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.Permission;
import com.monkeyzi.oauth.entity.domain.RolePermission;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.permission.PermissionDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.enums.SysEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.mapper.PermissionMapper;
import com.monkeyzi.oauth.service.PermissionService;
import com.monkeyzi.oauth.service.RolePermissionService;
import com.monkeyzi.oauth.utils.PublicUtil;
import com.monkeyzi.oauth.utils.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    @Autowired
    private RolePermissionService rolePermissionService;

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

    @Override
    @Transactional(rollbackFor = Exception.class,readOnly = true)
    public List<PermissionDto> getAllPermissionListByRoleId(String roleId, LoginAuthDto loginAuthDto) {
        //非超级管理员不能操作管理员角色
        if (roleId.equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_ROEL_ID)&&!loginAuthDto.getId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)){
            throw new BusinessException(ErrorCodeEnum.RS304);
        }
        //查询所有的菜单权限
        List<Permission> permissions=permissionMapper.selectAllPermissions();
        //查询该角色已经绑定的菜单权限
        List<RolePermission> rolePermissions=rolePermissionService.selectRolePermissionsByRoleId(roleId);
        //设置选中状态
        ModelMapper modelMapper=new ModelMapper();
        List<PermissionDto> dtoList=modelMapper.map(permissions,new TypeToken<List<PermissionDto>>() {}.getType());
        dtoList.forEach(a->{
            for (RolePermission rolePermission:rolePermissions){
                if (a.getId().equals(rolePermission.getRoleId())){
                    a.setChecked(true);
                    a.setSelected(true);
                    break;
                }
            }
        });
        //递归成树形结构
        return TreeUtils.getTree(dtoList,GlobalConstant.Sys.SYS_MENU_PERMISSION_ID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class,readOnly = true)
    public List<PermissionDto> getAllPermissionList() {
        //查询所有的菜单权限
        List<Permission> permissions=permissionMapper.selectAllPermissions();
        ModelMapper modelMapper=new ModelMapper();
        List<PermissionDto> dtoList=modelMapper.map(permissions,new TypeToken<List<PermissionDto>>() {}.getType());
        //递归成树形结构
        return TreeUtils.getTree(dtoList,GlobalConstant.Sys.SYS_MENU_PERMISSION_ID);
    }

    @Override
    public int addPermission(PermissionDto permissionDto, LoginAuthDto loginAuthUser) {
        ModelMapper modelMapper=new ModelMapper();
        Permission permission=modelMapper.map(permissionDto,Permission.class);

        Permission per=new Permission();
        per.setName(permissionDto.getName());
        Permission namePer=permissionMapper.selectOne(per);

        per.setName(null);
        per.setPath(permissionDto.getPath());
        Permission pathP=permissionMapper.selectOne(per);

        permission.setUpdateInfo(loginAuthUser);
        int result=0;
        if (permission.isNew()){
            //校验路径或者名称是否已经存在
            if (namePer!=null){
               throw new BusinessException(ErrorCodeEnum.PS101);
            }
            if (pathP!=null){
                throw new BusinessException(ErrorCodeEnum.PS102);
            }
            permission.setId(super.generateId());
            result= permissionMapper.insertSelective(permission);
        }else {
           //通过Id查询
           Permission perOne=permissionMapper.selectByPrimaryKey(permissionDto.getId());
           if (perOne==null){
               throw new BusinessException(ErrorCodeEnum.PS103);
           }
           //名称修改了
           if (!per.getName().equals(permissionDto.getName())){
               if (namePer!=null){
                   throw new BusinessException(ErrorCodeEnum.PS101);
               }
           }
           if (!pathP.getPath().equals(permissionDto.getPath())){
               if (pathP!=null){
                   throw new BusinessException(ErrorCodeEnum.PS102);
               }
           }
           result= permissionMapper.updateByPrimaryKeySelective(permission);
        }
        return result;
    }

    @Override
    public void deletePermission(List<String> ids) {
        Preconditions.checkArgument(PublicUtil.isNotEmpty(ids),ErrorCodeEnum.GL10001.getMsg());
        ids.forEach(a->{
            //删除权限
            Permission permission=new Permission();
            permission.setId(a);
            permissionMapper.deleteByPrimaryKey(a);
            //删除角色权限
            RolePermission rolePermission=new RolePermission();
            rolePermission.setPermissionId(a);
            rolePermissionService.delete(rolePermission);
        });
    }
}
