package com.monkeyzi.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.*;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.user.UserEditDto;
import com.monkeyzi.oauth.entity.dto.user.UserQueryDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.enums.UserSourceEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.mapper.*;
import com.monkeyzi.oauth.service.UserRoleService;
import com.monkeyzi.oauth.service.UserService;
import com.monkeyzi.oauth.utils.Md5Util;
import com.monkeyzi.oauth.utils.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDepartmentMapper userDepartmentMapper ;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private DepartMentMapper departMentMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserRoleService userRoleService;



    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public PageInfo queryUserListWithPage(UserQueryDto userQueryDto) {
        userQueryDto.setOrderBy(" last_login_time desc");
        PageHelper.startPage(userQueryDto.getPageNum(),userQueryDto.getPageSize());
        List<User> userList=userMapper.selectUserList(userQueryDto);
        PageInfo pageInfo=new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public int addUser(LoginAuthDto loginAuthDto, UserEditDto userEditDto) {
        Preconditions.checkArgument(PublicUtil.isNotEmpty(loginAuthDto),"没有获取到登录用户信息");
        //设置信息
        userEditDto.setUpdateInfo(loginAuthDto);
        String dePassword=userEditDto.getPassword();
        Preconditions.checkArgument(StringUtils.isNotBlank(dePassword),"密码不能为空");
        //加密
        userEditDto.setPassword(Md5Util.encrypt(dePassword));
        //验证用户名是否存在
        User user=new User();
        user.setUsername(userEditDto.getUsername());
        int  count=userMapper.selectCount(user);
        if (count>0){
            throw new BusinessException(ErrorCodeEnum.US002,userEditDto.getUsername());
        }
        ModelMapper modelMapper=new ModelMapper();
        User   mUser=modelMapper.map(userEditDto,User.class);
        mUser.setUserSource(UserSourceEnum.INSERT.getKey());
        mUser.setId(super.generateId());
        //保存用户
        int  result=userMapper.insertSelective(user);
        //添加组织关联
        UserDepartment userDept=new UserDepartment();
        //这里可以先查询一下部门存在不存在
        Department department=departMentMapper.selectByPrimaryKey(userEditDto.getDepartmentId());
        if (department==null){
            throw  new BusinessException(ErrorCodeEnum.DS201);
        }
        userDept.setDeptId(userEditDto.getDepartmentId());
        userDept.setUserId(mUser.getId());
        userDept.setUpdateInfo(loginAuthDto);
        userDepartmentMapper.insertSelective(userDept);
        //如果角色不为空,绑定角色关系
        if (PublicUtil.isNotEmpty(userEditDto.getRoles())){
            addUserRole(loginAuthDto,userEditDto);
        }
        return result;
    }

    @Override
    public int editUser(LoginAuthDto loginAuthDto, UserEditDto userEditDto) {
        Preconditions.checkArgument(PublicUtil.isNotEmpty(loginAuthDto),"没有获取到登录用户信息");
        //设置信息
        userEditDto.setUpdateInfo(loginAuthDto);
        if (StringUtils.isNotBlank(userEditDto.getId())){
            throw  new BusinessException(ErrorCodeEnum.US004);
        }
        User  user=userMapper.selectByPrimaryKey(userEditDto.getId());
        if (user==null){
            throw new BusinessException(ErrorCodeEnum.US003);
        }
        String oldName=user.getUsername();
        //修改了用户名的
        if (!userEditDto.getUsername().equals(oldName)){
            User user1=new User();
            user1.setUsername(oldName);
            User userName=userMapper.selectOne(user1);
            if (userName!=null){
                throw new BusinessException(ErrorCodeEnum.US002,oldName);
            }
        }
        int  result=userMapper.updateByPrimaryKeySelective(user);
        //处理部门信息
        UserDepartment userDepartment=new UserDepartment();
        userDepartment.setUserId(loginAuthDto.getId());
        userDepartment.setDeptId(userEditDto.getDepartmentId());
        userDepartment.setUpdateInfo(loginAuthDto);
        List<UserDepartment> userDeptList=userDepartmentMapper.selectDeptListByUserId(user.getId());

        if (PublicUtil.isNotEmpty(userDeptList)){
            userDepartment.setId(generateId());
            userDepartmentMapper.insertSelective(userDepartment);
        }else {
            //修改组织关联
            userDepartmentMapper.updateByUserId(userDepartment);
        }
        //处理角色信息
        //删除用户角色关系
        userRoleService.deleteUserRoleByUserId(userEditDto.getId());
        if (PublicUtil.isNotEmpty(userEditDto.getRoles())){
            addUserRole(loginAuthDto,userEditDto);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public User findUserByUserName(String userName) {
        Preconditions.checkArgument(StringUtils.isNotBlank(userName),ErrorCodeEnum.US001);
        User queryUser=new User();
        queryUser.setUsername(userName);
        User user=userMapper.selectOne(queryUser);
        if (user!=null){
           //查询用户的部门
           List<Department> userDeptList=departMentMapper.selectDeptListByUserId(user.getId());
           if (PublicUtil.isNotEmpty(userDeptList)){
               user.setDepartmentId(userDeptList.get(0).getId());
               user.setDepartmentName(userDeptList.get(0).getDeptName());
           }
           //查询用户的角色
           List<Role> roleList=roleMapper.selectAllRoleByUserId(user.getId());
           user.setRoles(roleList);
           //查询用户的权限
           List<Permission> permissions=permissionMapper.findByUserId(user.getId());
           user.setPermissions(permissions);
           return user;
        }
        return null;
    }

    /**
     * 添加用户角色关系
     * @param loginAuthDto
     * @param userEditDto
     */
    private void  addUserRole(LoginAuthDto loginAuthDto,UserEditDto userEditDto){
        userEditDto.getRoles().forEach(a->{
            UserRole userRole=new UserRole();
            userRole.setRoleId(a);
            userRole.setUserId(userEditDto.getId());
            userRole.setUpdateInfo(loginAuthDto);
            userRoleMapper.insertSelective(userRole);
        });
    }
}
