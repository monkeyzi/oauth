package com.monkeyzi.oauth.mapper;

import com.monkeyzi.oauth.base.mybatis.MyMapper;
import com.monkeyzi.oauth.entity.domain.Role;
import com.monkeyzi.oauth.entity.dto.role.RoleQueryDto;
import com.monkeyzi.oauth.entity.dto.roleuser.BindRoleDto;
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

    /**
     * 获取所有可以绑定的角色
     * @return
     */
    List<BindRoleDto> getAllBindRoleList();

    /**
     * 分页查询系统所有可用角色
     * @param roleQueryDto
     * @return
     */
    List<Role>  getAllRoleListWithPage(RoleQueryDto roleQueryDto);
}
