package com.monkeyzi.oauth.mapper;

import com.monkeyzi.oauth.base.mybatis.MyMapper;
import com.monkeyzi.oauth.entity.domain.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author: 高yg
 * @date: 2018/10/20 22:32
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Component
@Mapper
public interface RolePermissionMapper extends MyMapper<RolePermission> {
}
