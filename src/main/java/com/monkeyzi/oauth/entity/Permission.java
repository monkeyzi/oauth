package com.monkeyzi.oauth.entity;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "t_permission")
public class Permission extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单/权限名称")
    private String name;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "类型 0页面 1具体操作")
    private Integer type;
}
