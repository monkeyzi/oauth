package com.monkeyzi.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.User;
import com.monkeyzi.oauth.entity.dto.user.UserQueryDto;
import com.monkeyzi.oauth.mapper.UserMapper;
import com.monkeyzi.oauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
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


    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public PageInfo queryUserListWithPage(UserQueryDto userQueryDto) {
        userQueryDto.setOrderBy(" last_login_time desc");
        PageHelper.startPage(userQueryDto.getPageNum(),userQueryDto.getPageSize());
        List<User> userList=userMapper.selectUserList(userQueryDto);
        PageInfo pageInfo=new PageInfo(userList);
        return pageInfo;
    }
}
