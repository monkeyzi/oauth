package com.monkeyzi.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.*;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.roleuser.BindRoleDto;
import com.monkeyzi.oauth.entity.dto.roleuser.BindUserRolesDto;
import com.monkeyzi.oauth.entity.dto.user.UserEditDto;
import com.monkeyzi.oauth.entity.dto.user.UserQueryDto;
import com.monkeyzi.oauth.entity.vo.roleuser.BindRoleVo;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.enums.UserSourceEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.mapper.*;
import com.monkeyzi.oauth.service.*;
import com.monkeyzi.oauth.utils.Md5Util;
import com.monkeyzi.oauth.utils.PublicUtil;
import com.monkeyzi.oauth.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private RoleService roleService;

    @Autowired
    private CommonService commonService;

    @Resource
    private TaskExecutor taskExecutor;

    @Autowired
    private UserTokenService userTokenService;


    @Override
    public void handlerLoginData(OAuth2AccessToken token, User principal, HttpServletRequest request) {
        String  userName=principal.getUsername();
        User    user=this.findUserByUserName(userName);
        User    user1=new User();
        String  ipAddr=RequestUtils.getRemoteAddr(request);
        String  location=commonService.getAddressLocationByIp(ipAddr);
        user1.setId(user.getId());
        user1.setLastLoginIp(ipAddr);
        user1.setLastLoginLocation(location);
        user1.setLastLoginTime(new Date());

        //设置授权用户信息
        LoginAuthDto loginAuthDto=new LoginAuthDto();
        loginAuthDto.setId(user.getId());
        loginAuthDto.setUserName(user.getUsername());
        loginAuthDto.setNickName(user.getNickName());
        loginAuthDto.setPermissions(user.getPermissions());
        loginAuthDto.setRoles(user.getRoles());
        loginAuthDto.setRoleList(user.getRoles().stream().map(a->a.getId()).collect(Collectors.toList()));
        // 记录用户的token信息
        userTokenService.saveUserToken(token,loginAuthDto,request);
        // 更新用户的最后登录信息
        taskExecutor.execute(()->{
            int result=userMapper.updateByPrimaryKeySelective(user1);
            if (result>0){
                log.info("更新用户的登录信息成功");
            }else {
                log.error("更新登录信息  失败了");
            }
        });

    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public PageInfo queryUserListWithPage(UserQueryDto userQueryDto) {
        userQueryDto.setOrderBy("create_time desc,last_login_time desc");
        if (StringUtils.isNotEmpty(userQueryDto.getEndTime())){
            userQueryDto.setEndTime(userQueryDto.getEndTime()+" 23:59:59");
        }
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
        int  result=userMapper.insertSelective(mUser);
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
        userDept.setId(super.generateId());
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
        if (StringUtils.isBlank(userEditDto.getId())){
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
           //List<String> roleIds=roleList.stream().map(a->a.getId()).collect(Collectors.toList());

           //查询用户的权限
           List<Permission> permissions=permissionMapper.findByUserId(user.getId());
           user.setPermissions(permissions);
           return user;
        }
        return null;
    }

    @Override
    public int modifyUserStatus(String userId, LoginAuthDto loginAuthUser,Integer status) {
        Preconditions.checkArgument(StringUtil.isNotEmpty(userId),ErrorCodeEnum.US004.getMsg());
        //不允许修改自己
        if (userId.equals(loginAuthUser.getId())&&!userId.equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)){
            throw  new BusinessException(ErrorCodeEnum.US005);
        }
        //超级管理员不允许操作
        if (userId.equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)&&
                !loginAuthUser.getId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)){
            log.error("越权操作,不能操作管理员账户");
            throw  new BusinessException(ErrorCodeEnum.US006);
        }
        User userFind=userMapper.selectByPrimaryKey(userId);
        if(userFind==null){
            throw  new BusinessException(ErrorCodeEnum.US003);
        }

        User userChange=new User();
        userChange.setId(userId);
        userChange.setStatus(status);
        userChange.setUpdateInfo(loginAuthUser);
        int  result=userMapper.updateByPrimaryKeySelective(userChange);
        return result;
    }

    @Override
    public void deleteUserByUserIds(List<String> ids, LoginAuthDto loginAuthDto) {
        if (PublicUtil.isEmpty(ids)){
            throw new BusinessException(ErrorCodeEnum.GL10001);
        }
        if (ids.contains(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)){
            throw new BusinessException(ErrorCodeEnum.US006);
        }
        //删除用户
        ids.forEach(a->{
            userMapper.deleteByPrimaryKey(a);
            //删除用户部门关系
            UserDepartment userDept=new UserDepartment();
            userDept.setUserId(a);
            userDepartmentMapper.delete(userDept);
            //删除用户角色关系
            userRoleMapper.deleteUserRoleByUserId(a);
        });

    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public BindRoleVo getBindUserRoleList(String userId, LoginAuthDto loginAuthDto) {
        Preconditions.checkArgument(StringUtils.isNotBlank(userId),ErrorCodeEnum.US004.getMsg());
        //不允许操作自己
        if (Objects.equals(userId,loginAuthDto.getId())&&!userId.equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)){
            throw  new BusinessException(ErrorCodeEnum.US005);
        }
        //不允许操作超级管理员账户
        //超级管理员不允许操作
        if (userId.equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)&&
                !loginAuthDto.getId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)){
            log.error("越权操作,不能操作管理员账户");
            throw  new BusinessException(ErrorCodeEnum.US006);
        }
        //查询所有的角色列表
        Set<BindRoleDto> allBindRoleList=roleService.allBindRoleList().stream().collect(Collectors.toSet());
        //查询用户已经绑定的角色列表
        List<UserRole> userRoles=userRoleService.getBindRoleByUserId(userId);
        //组装数据返回
        allBindRoleList.forEach(a->{
            a.setBind(false);
            if (userRoles.size()>0){
                for (UserRole ur:userRoles){
                    if (ur.getRoleId().equals(a.getRoleId())){
                        a.setBind(true);
                        break;
                    }
                }
            }
        });
        BindRoleVo bindRoleVo=new BindRoleVo();
        bindRoleVo.setBindRoleSet(allBindRoleList);
        return bindRoleVo;
    }

    @Override
    public void bindUserRole(BindUserRolesDto bindUserRolesDto, LoginAuthDto loginAuthDto) {
        Preconditions.checkArgument(PublicUtil.isNotEmpty(bindUserRolesDto),ErrorCodeEnum.GL10001.getMsg());
        //不允许操作自己
        if (Objects.equals(bindUserRolesDto.getUserId(),loginAuthDto.getId())&&!
                bindUserRolesDto.getUserId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)){
            throw  new BusinessException(ErrorCodeEnum.US005);
        }
        //不允许操作超级管理员账户
        if (bindUserRolesDto.getUserId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)&&
                !loginAuthDto.getId().equals(GlobalConstant.Sys.SYS_SUPER_ADMIN_USER_ID)){
            log.error("越权操作,不能操作管理员账户");
            throw  new BusinessException(ErrorCodeEnum.US006);
        }
        List<String> bindRoleList=bindUserRolesDto.getRoleIdList();

        User user=userMapper.selectByPrimaryKey(bindUserRolesDto.getUserId());
        if (user==null){
            log.error("没有查询到用户信息 userId={}",bindUserRolesDto.getUserId());
        }
        //查询用户已经绑定的角色
        List<UserRole> userRoleList=userRoleService.getBindRoleByUserId(bindUserRolesDto.getUserId());
        if(PublicUtil.isNotEmpty(userRoleList)){
            //删除
            userRoleService.deleteUserRoleByUserId(bindUserRolesDto.getUserId());
        }
        //更新用户的操作信息
        final  User opUser=new User();
        opUser.setId(bindUserRolesDto.getUserId());
        opUser.setUpdateInfo(loginAuthDto);
        userMapper.updateByPrimaryKeySelective(opUser);
        if (PublicUtil.isEmpty(bindRoleList)){
            log.info("用户角色解绑成功  userId={}",bindUserRolesDto.getUserId());
        }

        bindRoleList.forEach(a->{
           Role role=roleMapper.selectByPrimaryKey(a);
           if (role==null){
               throw new BusinessException(ErrorCodeEnum.RS301,a);
           }
           UserRole userRole=new UserRole();
           userRole.setUserId(bindUserRolesDto.getUserId());
           userRole.setRoleId(a);
           userRole.setUpdateInfo(loginAuthDto);
           userRoleMapper.insertSelective(userRole);
        });

    }

    @Override
    public User findUserByLoginName(String userName) {
        Preconditions.checkArgument(StringUtils.isNotBlank(userName),ErrorCodeEnum.US001);
        User queryUser=new User();
        queryUser.setUsername(userName);
        User user=userMapper.selectOne(queryUser);
        return user;
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
