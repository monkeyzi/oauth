package com.monkeyzi.oauth.entity.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(value = "新增/或者修改角色dto")
public class RoleDto implements Serializable {

    private static final long serialVersionUID = -3385971785265488527L;

    @ApiModelProperty(value = "角色Id")
    private String id;

    @ApiModelProperty(value = "角色名,以ROLE_开头命名")
    @Pattern(regexp = "^ROLE_.*", message = "角色名要以ROLE_开头命名")
    @NotBlank(message = "角色名不能为空")
    private String roleName;

    @NotBlank(message = "角色编码不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "角色编码只能是数字和字母组合")
    @Length(min = 6, max = 20, message = "角色编码长度要在6-20字符之间")
    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "默认角色 1是 0不是")
    private Integer defaultRole;



}
