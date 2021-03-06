package com.monkeyzi.oauth.mapper;

import com.monkeyzi.oauth.base.mybatis.MyMapper;
import com.monkeyzi.oauth.entity.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/18 22:45
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Mapper
@Component
public interface UserRoleMapper  extends MyMapper<UserRole> {

    /**
     * 删除用户角色关系
     * @param userId
     * @return
     */
    int deleteUserRoleByUserId(String userId);

    /**
     * 查询用户已经绑定的角色
     * @param userId
     * @return
     */
    List<UserRole> listByUserId(String userId);
}
