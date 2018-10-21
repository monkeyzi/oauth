package com.monkeyzi.oauth.entity.dto.roleuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 高yg
 * @date: 2018/10/21 20:18
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@ApiModel(value = "角色dto")
public class BindRoleDto implements Serializable {

    private static final long serialVersionUID = -3385971785265488527L;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色Id")
    private String roleId;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;


    /**
     * 用户是否已经绑定该角色
     */
    @ApiModelProperty(value = "是否已经绑定")
    private boolean isBind;



}
