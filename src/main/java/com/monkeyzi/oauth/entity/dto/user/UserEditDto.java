package com.monkeyzi.oauth.entity.dto.user;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/18 20:52
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@ApiModel(value = "新增/修改用户参数")
public class UserEditDto extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名/登录名",required = true)
    @NotBlank(message = "登录名不能为空")
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "昵称/姓名",required = true)
    @NotBlank(message = "姓名不能为空")
    private String nickName;

    @ApiModelProperty(value = "描述/详情/备注")
    private String description;

    @ApiModelProperty(value = "手机",required = true)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "邮件",required = true)
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer userType;

    @ApiModelProperty(value = "用户编码",required = true)
    @NotBlank(message = "用户编码不能为空")
    private String userCode;

    @ApiModelProperty(value = "用户头像")
    private String userAvatar;

    @ApiModelProperty(value = "0女 1男 2保密",required = true)
    @NotNull(message = "性别不能为空")
    private Integer sex;

    @ApiModelProperty(value = "所属部门id",required = true)
    @NotBlank(message = "所属部门不能为空")
    private String departmentId;

    @ApiModelProperty(value = "角色Id")
    private List<String> roles;
}
