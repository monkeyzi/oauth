package com.monkeyzi.oauth.service.impl;

import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.UserRole;
import com.monkeyzi.oauth.mapper.UserRoleMapper;
import com.monkeyzi.oauth.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 高yg
 * @date: 2018/10/20 18:55
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void deleteUserRoleByUserId(String userId) {
       log.info("删除用户的角色 userId={}",userId);
       userRoleMapper.deleteUserRoleByUserId(userId);
    }
}
