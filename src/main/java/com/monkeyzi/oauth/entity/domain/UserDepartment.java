package com.monkeyzi.oauth.entity.domain;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @author: 高yg
 * @date: 2018/10/18 22:29
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@ApiModel(value = "用户组织关系")
@Alias(value = "userDepartment")
public class UserDepartment extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "组织Id")
    private String deptId;


}
