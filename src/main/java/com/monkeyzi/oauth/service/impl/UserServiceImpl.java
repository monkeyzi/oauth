package com.monkeyzi.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.Role;
import com.monkeyzi.oauth.entity.domain.User;
import com.monkeyzi.oauth.entity.domain.UserDepartment;
import com.monkeyzi.oauth.entity.domain.UserRole;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.user.UserEditDto;
import com.monkeyzi.oauth.entity.dto.user.UserQueryDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.enums.UserSourceEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.mapper.RoleMapper;
import com.monkeyzi.oauth.mapper.UserDepartmentMapper;
import com.monkeyzi.oauth.mapper.UserMapper;
import com.monkeyzi.oauth.mapper.UserRoleMapper;
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
        //这里可以先查询一下部门存在不存在   TODO
        userDept.setDeptId(userEditDto.getDepartmentId());
        userDept.setUserId(mUser.getId());
        userDept.setUpdateInfo(loginAuthDto);
        userDepartmentMapper.insertSelective(userDept);
        //如果角色不为空,绑定角色关系
        if (PublicUtil.isNotEmpty(userEditDto.getRoles())){
            userEditDto.getRoles().forEach(a->{
                UserRole userRole=new UserRole();
                userRole.setRoleId(a);
                userRole.setUserId(mUser.getId());
                Role role=roleMapper.selectByPrimaryKey(a);
                if (role!=null){
                    userRole.setRoleName(role.getRoleName());
                }
                userRole.setUpdateInfo(loginAuthDto);
                userRoleMapper.insertSelective(userRole);
            });
        }
        return result;
    }

    @Override
    public int editUser(LoginAuthDto loginAuthDto, UserEditDto userEditDto) {

        return 0;
    }

    @Override
    public User findUserByUserName(String userName) {
        Preconditions.checkArgument(StringUtils.isNotBlank(userName),ErrorCodeEnum.US001);
        User queryUser=new User();
        queryUser.setUsername(userName);
        User user=userMapper.selectOne(queryUser);
        if (user!=null){
           //查询用户的部门

           //查询用户的角色

           //查询用户的权限
            return user;
        }
        return null;
    }
}
