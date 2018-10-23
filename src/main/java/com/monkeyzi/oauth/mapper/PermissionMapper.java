package com.monkeyzi.oauth.mapper;

import com.monkeyzi.oauth.base.mybatis.MyMapper;
import com.monkeyzi.oauth.entity.domain.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/13 18:19
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Component
@Mapper
public interface PermissionMapper extends MyMapper<Permission> {

    /**
     * 查询用户拥有的页面权限和菜单权限
     * @param userId
     * @return
     */
    List<Permission> findByUserId(String userId);

    /**
     * 查询所有的菜单权限
     * @return
     */
    List<Permission> selectAllPermissions();

}
