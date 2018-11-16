package com.monkeyzi.oauth.entity.dto.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "文件夹参数")
public class FolderDto {

    @ApiModelProperty(value = "文件夹Id")
    private String id;

    @ApiModelProperty(value = "文件夹名称",required = true)
    @NotBlank(message = "文件夹名称不能为空")
    private String folderName;

    @ApiModelProperty(value = "父Id",required = true)
    @NotBlank(message = "父Id不能为空")
    private String parentId;

    @ApiModelProperty(value = "层级")
    private String level;

    @ApiModelProperty(value = "描述")
    private String description;



}
