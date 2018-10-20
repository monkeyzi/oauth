package com.monkeyzi.oauth.service.impl;

import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.Role;
import com.monkeyzi.oauth.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
