package com.monkeyzi.oauth.entity.dto.role;

import com.monkeyzi.oauth.entity.domain.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "禁用角色")
public class DisableRoleDto implements Serializable {

    @ApiModelProperty(value = "禁用角色")
    private  List<Role> roleList;
}
