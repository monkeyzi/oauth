package com.monkeyzi.oauth.entity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Alias(value = "user")
@Table(name = "m_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty(value = "昵称/姓名")
    private String nickName;

    @ApiModelProperty(value = "状态 1：正常 0：未激活 3：被禁用")
    private Integer status;

    @ApiModelProperty(value = "描述/详情/备注")
    private String description;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer userType;

    @ApiModelProperty(value = "用户来源")
    private String userSource;

    @ApiModelProperty(value = "用户编码")
    private String userCode;

    @ApiModelProperty(value = "最后一次登录的ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "最后一次登录位置")
    private String lastLoginLocation;

    @ApiModelProperty(value = "是否更改过密码  0没有  1有")
    private Integer isChangedPwd;

    @ApiModelProperty(value = "最后一次登录时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    @ApiModelProperty(value = "最后一次输错密码的时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pwdErrorTime;

    @ApiModelProperty(value = "连续输错密码次数")
    private Integer pwdErrorCount;

    @ApiModelProperty(value = "用户头像")
    private String userAvatar;

    @ApiModelProperty(value = "0女 1男 2保密")
    private Integer sex;

    @Transient
    @ApiModelProperty(value = "所属部门id")
    private String departmentId;

    @Transient
    @ApiModelProperty(value = "所属部门名称")
    private String departmentName;

    @Transient
    @ApiModelProperty(value = "用户拥有角色")
    private List<Role> roles;

    @Transient
    @ApiModelProperty(value = "用户拥有的权限")
    private List<Permission> permissions;
}
