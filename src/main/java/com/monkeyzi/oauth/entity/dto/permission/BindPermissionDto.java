package com.monkeyzi.oauth.entity.dto.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/22 20:07
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@ApiModel(value = "角色绑定权限参数")
public class BindPermissionDto implements Serializable {

    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "角色id不能为空")
    private String roleId;

    @ApiModelProperty(value = "权限Id集合")
    @NotBlank(message = "绑定权限不能为空")
    private List<String> permissions;
}
