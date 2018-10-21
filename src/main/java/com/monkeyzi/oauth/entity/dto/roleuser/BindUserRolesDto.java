package com.monkeyzi.oauth.entity.dto.roleuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/21 20:06
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@ApiModel
@Data
public class BindUserRolesDto implements Serializable {

    private static final long serialVersionUID = -9149237379943908522L;
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "需要绑定的角色ID集合")
    private List<String> roleIdList;
}
