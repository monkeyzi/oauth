package com.monkeyzi.oauth.service;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.User;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.roleuser.BindUserRolesDto;
import com.monkeyzi.oauth.entity.dto.user.UserEditDto;
import com.monkeyzi.oauth.entity.dto.user.UserQueryDto;
import com.monkeyzi.oauth.entity.vo.roleuser.BindRoleVo;

import java.util.List;

public interface UserService extends BaseService<User> {

    /**
     * 分页查询用户信息
      * @param userQueryDto
     * @return
     */
   PageInfo queryUserListWithPage(UserQueryDto userQueryDto);

    /**
     * 后台添加用户
     * @param loginAuthDto
     * @param userEditDto
     * @return
     */
    int addUser(LoginAuthDto loginAuthDto, UserEditDto userEditDto);

    /**
     * 后台修改用户
     * @param loginAuthDto
     * @param userEditDto
     * @return
     */
    int editUser(LoginAuthDto loginAuthDto, UserEditDto userEditDto);

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    User findUserByUserName(String userName);

    /**
     * 修改用户的状态
     * @param userId
     * @param loginAuthUser
     * @return
     */
    int modifyUserStatus(String userId, LoginAuthDto loginAuthUser,Integer status);

    /**
     * 删除用户
     * @param ids
     * @param loginAuthDto
     */
    void deleteUserByUserIds(List<String> ids, LoginAuthDto loginAuthDto);

    /**
     * 查询用户绑定的角色
     * @param userId
     * @param loginAuthDto
     * @return
     */
    BindRoleVo getBindUserRoleList(String userId, LoginAuthDto loginAuthDto);


    /**
     * 用户绑定角色
     * @param bindUserRolesDto
     * @param loginAuthDto
     */
    void bindUserRole(BindUserRolesDto bindUserRolesDto, LoginAuthDto loginAuthDto);
}
