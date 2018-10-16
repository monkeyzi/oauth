package com.monkeyzi.oauth.entity.domain;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author: 高yg
 * @date: 2018/10/14 18:07
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@Table(name = "m_user_role")
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "角色Id")
    private String roleId;

    @Transient
    @ApiModelProperty(value = "角色名")
    private String roleName;
}
