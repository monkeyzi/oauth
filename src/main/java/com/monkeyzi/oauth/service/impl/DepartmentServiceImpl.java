package com.monkeyzi.oauth.service.impl;

import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.Department;
import com.monkeyzi.oauth.service.DepartMentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartMentService {
}
