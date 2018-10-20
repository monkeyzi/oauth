package com.monkeyzi.oauth.mapper;

import com.monkeyzi.oauth.base.mybatis.MyMapper;
import com.monkeyzi.oauth.entity.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/18 22:44
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Mapper
@Component
public interface RoleMapper extends MyMapper<Role> {


    /**
     * 根据用户Id查询用户的角色信息
     * @param userId
     * @return
     */
    List<Role> selectAllRoleByUserId(String userId);
}
