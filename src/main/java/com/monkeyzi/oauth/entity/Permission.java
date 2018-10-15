package com.monkeyzi.oauth.entity;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Table;
import java.math.BigDecimal;
@EqualsAndHashCode(callSuper = false)
@Data
@Table(name = "m_permission")
@Alias(value = "permission")
public class Permission extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态 0：可用  1:不可用")
    private Integer status;

    @ApiModelProperty(value = "菜单/权限描述")
    private String  description;

    @ApiModelProperty(value = "菜单/权限名称")
    private String name;

    @ApiModelProperty(value = "菜单/权限父Id")
    private String parentId;

    @ApiModelProperty(value = "类型 0页面 1具体操作")
    private Integer type;

    @ApiModelProperty(value = "排序")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "按钮类型")
    private String buttonType;

    @ApiModelProperty(value = "其他链接地址")
    private String url;


}
