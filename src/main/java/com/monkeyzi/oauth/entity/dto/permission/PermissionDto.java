package com.monkeyzi.oauth.entity.dto.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/22 21:26
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@ApiModel
public class PermissionDto  implements Serializable {

    @ApiModelProperty(value = "id---修改的时候必传")
    private String  id;

    @ApiModelProperty(value = "状态 0：可用  1:不可用")
    private Integer status;

    @ApiModelProperty(value = "菜单/权限描述")
    private String  description;

    @ApiModelProperty(value = "菜单/权限名称",required = true)
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "菜单/权限父Id",required = true)
    @NotBlank(message = "菜单/权限父Id不能为空")
    private String parentId;

    @ApiModelProperty(value = "类型 0页面 1具体操作",required = true)
    @NotBlank(message = "权限类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "排序-必填",required = true)
    @NotBlank(message = "排序不能为空")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "路径-必填",required = true)
    @NotBlank(message = "路径不能为空")
    private String path;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "层级-必填",required = true)
    @NotNull(message = "层级不能为空")
    private Integer level;

    @ApiModelProperty(value = "按钮类型")
    private String buttonType;

    @ApiModelProperty(value = "其他链接地址")
    private String url;

    @ApiModelProperty(value = "子菜单/权限")
    private List<PermissionDto> children;

    @ApiModelProperty(value = "节点展开 前端所需")
    private Boolean expand = true;

    @ApiModelProperty(value = "是否勾选 前端所需")
    private Boolean checked = false;

    @ApiModelProperty(value = "是否选中 前端所需")
    private Boolean selected = false;

    @ApiModelProperty(value = "是否有孩子节点")
    private Boolean hasChild= false;


}
