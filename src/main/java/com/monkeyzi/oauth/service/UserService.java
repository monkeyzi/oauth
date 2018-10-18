package com.monkeyzi.oauth.service;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.User;
import com.monkeyzi.oauth.entity.dto.user.UserQueryDto;

public interface UserService extends BaseService<User> {

    /**
     * 分页查询用户信息
      * @param userQueryDto
     * @return
     */
   PageInfo queryUserListWithPage(UserQueryDto userQueryDto);


}
