package com.monkeyzi.oauth.entity.dto.tree;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "树形结构")
public class TreeDto {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "父id")
    private String parentId;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "层级")
    private String level;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "孩子列表")
    private List<TreeDto> children;

    @ApiModelProperty(value = "是否有孩子节点")
    private Boolean hasChild= false;

    @ApiModelProperty(value = "创建人")
    private String createBy;

}
