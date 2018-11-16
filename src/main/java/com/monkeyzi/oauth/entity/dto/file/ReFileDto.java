package com.monkeyzi.oauth.entity.dto.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "文件修改")
public class ReFileDto {

    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "文件Id",required = true)
    private String id;

    @NotBlank(message = "新名称不能为空")
    @ApiModelProperty(value = "新名称不能为空",required = true)
    private String newName;

}
