package com.monkeyzi.oauth.service;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.User;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.user.UserEditDto;
import com.monkeyzi.oauth.entity.dto.user.UserQueryDto;

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
}
