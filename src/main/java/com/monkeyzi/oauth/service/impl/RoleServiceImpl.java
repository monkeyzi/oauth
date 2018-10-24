package com.monkeyzi.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.Role;
import com.monkeyzi.oauth.entity.domain.UserRole;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.role.RoleDefaultDto;
import com.monkeyzi.oauth.entity.dto.role.RoleDto;
import com.monkeyzi.oauth.entity.dto.role.RoleQueryDto;
import com.monkeyzi.oauth.entity.dto.roleuser.BindRoleDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.enums.RoleStatusEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.mapper.RoleMapper;
import com.monkeyzi.oauth.mapper.UserRoleMapper;
import com.monkeyzi.oauth.service.RoleService;
import com.monkeyzi.oauth.utils.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/20 18:58
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<BindRoleDto> allBindRoleList() {
        return roleMapper.getAllBindRoleList();
    }

    @Override
    public int addRole(RoleDto roleDto, LoginAuthDto loginAuthDto) {
        Role role=new Role();
        role.setRoleCode(roleDto.getRoleCode());
        List<Role> roleByCode=roleMapper.select(role);

        role.setRoleCode(null);
        role.setRoleName(roleDto.getRoleName());
        List<Role> roleByName=roleMapper.select(role);

        BeanUtils.copyProperties(roleDto,role);
        //判断角色名和角色编码是否已经存在
        role.setUpdateInfo(loginAuthDto);
        int result=0;
        if (role.isNew()){
            if (roleByCode.size()>0){
                log.error("角色编码已经存在 roleCode={}",roleDto.getRoleCode());
                throw new BusinessException(ErrorCodeEnum.RS303,roleDto.getRoleCode());
            }
            if (roleByName.size()>0){
                log.error("角色名称已经存在 roleName={}",roleDto.getRoleName());
                throw new BusinessException(ErrorCodeEnum.RS302,roleDto.getRoleName());
            }
            role.setId(super.generateId());
            result=roleMapper.insertSelective(role);
        }else {
            //根据id查询
            Role roleById=roleMapper.selectByPrimaryKey(roleDto.getId());
            if (roleById==null){
                throw new BusinessException(ErrorCodeEnum.RS307,roleDto.getId());
            }
            //若名称修改了
            if (!roleById.getRoleName().equals(roleDto.getRoleName())){
                if (roleByName.size()>0){
                    log.error("角色名称已经存在 roleName={}",roleDto.getRoleName());
                    throw new BusinessException(ErrorCodeEnum.RS302,roleDto.getRoleName());
                }
            }
            if (!roleById.getRoleCode().equals(roleDto.getRoleCode())){
                if (roleByCode.size()>0){
                    log.error("角色编码已经存在 roleCode={}",roleDto.getRoleCode());
                    throw new BusinessException(ErrorCodeEnum.RS303,roleDto.getRoleCode());
                }
            }
            result=roleMapper.updateByPrimaryKeySelective(role);
        }
        return result;
    }



    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<Role> getAllRoleList() {
        Role role=new Role();
        role.setStatus(RoleStatusEnum.ENABLE_ROLE.code);
        List<Role> roleList=roleMapper.select(role);
        return roleList;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public PageInfo getAllRoleListWithPage(RoleQueryDto queryDto) {
        PageHelper.startPage(queryDto.getPageNum(),queryDto.getPageSize());
        PageHelper.orderBy(" create_time desc");
        List<Role> roleList=roleMapper.getAllRoleListWithPage(queryDto);
        PageInfo pageInfo=new PageInfo(roleList);
        return pageInfo;
    }

    @Override
    public int setDefaultRole(RoleDefaultDto roleDefaultDto, LoginAuthDto loginAuthUser) {
        //给管理员账号不能操作管理员角色
        if (!loginAuthUser.getId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)
                &&roleDefaultDto.getRoleId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_ROEL_ID)){
           throw new BusinessException(ErrorCodeEnum.RS304);
        }
        //确保系统最多只有一个默认角色
        Role roleDefault=roleMapper.selectByPrimaryKey(roleDefaultDto.getRoleId());
        if (roleDefault!=null){
            throw new BusinessException(ErrorCodeEnum.RS305);
        }
        Role role=new Role();
        role.setId(roleDefaultDto.getRoleId());
        role.setDefaultRole(roleDefaultDto.getDefaultRole());
        role.setUpdateInfo(loginAuthUser);
        int result=roleMapper.updateByPrimaryKeySelective(role);
        return result;
    }

    @Override
    public void deleteRole(List<String> ids, LoginAuthDto loginAuthUser) {
        if (PublicUtil.isEmpty(ids)){
             throw  new BusinessException(ErrorCodeEnum.US004);
        }
        if (ids.contains(GlobalConstant.Sys.SYS_SUPER_ADMIN_ROEL_ID)&&
                !loginAuthUser.getId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)){
            throw new  BusinessException(ErrorCodeEnum.RS304);
        }
        //删除角色
        ids.forEach(a->{
            roleMapper.deleteByPrimaryKey(a);
            //删除用户角色关系
            UserRole userRole=new UserRole();
            userRole.setRoleId(a);
            userRoleMapper.delete(userRole);
        });
    }

    @Override
    public void disableRole(List<String> ids, LoginAuthDto loginAuthUser,Integer status) {
        if (PublicUtil.isEmpty(ids)){
            throw  new BusinessException(ErrorCodeEnum.US004);
        }
        if (ids.contains(GlobalConstant.Sys.SYS_SUPER_ADMIN_ROEL_ID)&&
                !loginAuthUser.getId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)){
            throw new  BusinessException(ErrorCodeEnum.RS304);
        }
        ids.forEach(a->{
            Role role=new Role();
            role.setId(a);
            role.setStatus(status);
            role.setUpdateInfo(loginAuthUser);
            roleMapper.updateByPrimaryKeySelective(role);
        });
    }

}
