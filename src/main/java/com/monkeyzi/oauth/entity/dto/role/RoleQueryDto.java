package com.monkeyzi.oauth.entity.dto.role;

import com.monkeyzi.oauth.base.BaseQueryDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel(value = "角色查询参数")
@EqualsAndHashCode(callSuper = true)
public class RoleQueryDto extends BaseQueryDto implements Serializable {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;



}
