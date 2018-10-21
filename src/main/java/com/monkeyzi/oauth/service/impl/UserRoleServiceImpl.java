package com.monkeyzi.oauth.service.impl;

import com.google.common.base.Preconditions;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.UserRole;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.mapper.UserRoleMapper;
import com.monkeyzi.oauth.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<UserRole> getBindRoleByUserId(String userId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(userId),ErrorCodeEnum.US004.getMsg());
        return userRoleMapper.listByUserId(userId);
    }
}
