package com.monkeyzi.oauth.entity.domain;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "m_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "角色状态 0可用   1：不可用")
    private Integer status;

    @ApiModelProperty(value = "角色描述")
    private String description;


    @ApiModelProperty(value = "是否是默认角色   1是 0不是")
    private Integer defaultRole;

}
