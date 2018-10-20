package com.monkeyzi.oauth.service.impl;

import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.RolePermission;
import com.monkeyzi.oauth.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 高yg
 * @date: 2018/10/20 19:00
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements RolePermissionService {
}
