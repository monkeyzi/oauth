package com.monkeyzi.oauth.entity.domain;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * @author: 高yg
 * @date: 2018/10/14 18:12
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@Table(name = "m_role_permission")
@EqualsAndHashCode(callSuper = true)
public class RolePermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "权限id")
    private String permissionId;
}
