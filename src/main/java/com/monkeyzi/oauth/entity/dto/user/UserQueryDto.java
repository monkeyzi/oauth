package com.monkeyzi.oauth.entity.dto.user;

import com.monkeyzi.oauth.base.BaseQueryDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "用户查询参数")
@EqualsAndHashCode(callSuper = true)
public class UserQueryDto extends BaseQueryDto implements Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录名")
    private String  username;

    @ApiModelProperty(value = "用户姓名")
    private String  nickName;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "部门编号")
    private String  deptId;

    @ApiModelProperty(value = "手机号")
    private String  phone;

    @ApiModelProperty(value = "邮箱")
    private String  email;

    @ApiModelProperty(value = "性别")
    private Integer userType;

    @ApiModelProperty(value = "登录位置")
    private String  lastLoginLocation;

    @ApiModelProperty(value = "登录ip")
    private String  lastLoginIp;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间--起始时间")
    private String  startTime;

    @ApiModelProperty(value = "创建时间--结束时间")
    private String  endTime;

    private Date    queryStartTime;

    private Date    queryEndTime;




}
