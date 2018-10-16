package com.monkeyzi.oauth.entity.dto;

import com.monkeyzi.oauth.entity.domain.Permission;
import com.monkeyzi.oauth.entity.domain.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "授权用户")
public class LoginAuthDto {

    @ApiModelProperty(value = "用户Id")
    private String id;

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String nickName;

    @ApiModelProperty(value = "角色列表")
    private List<Role> roles;

    @ApiModelProperty(value = "权限列表")
    private List<Permission> permissions;

    @ApiModelProperty(value = "部门编号")
    private String departmentId;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

}
