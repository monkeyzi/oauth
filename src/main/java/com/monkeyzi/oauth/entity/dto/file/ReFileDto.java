package com.monkeyzi.oauth.entity.dto.file;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "文件修改")
public class ReFileDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "新名称不能为空")
    private String newName;

}
