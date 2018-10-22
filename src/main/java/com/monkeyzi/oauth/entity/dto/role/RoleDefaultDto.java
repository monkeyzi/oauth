package com.monkeyzi.oauth.entity.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value = "设置默认角色参数")
public class RoleDefaultDto  implements Serializable {

    private static final long serialVersionUID = -3385971785265488527L;

    @ApiModelProperty(value = "角色Id")
    @NotBlank(message = "角色Id不能为空")
    private String roleId;

    @ApiModelProperty(value = "默认角色状态 1是 0不是")
    @NotBlank(message = "默认角色状态不能为空")
    private Integer defaultRole;


}
