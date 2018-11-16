package com.monkeyzi.oauth.entity.dto.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel
public class DeptDto implements Serializable {

    @ApiModelProperty(value = "id")
    private String  id;

    @ApiModelProperty(value = "状态 0：可用  1:不可用")
    private Integer status;

    @ApiModelProperty(value = "父ID",required = true)
    @NotBlank(message = "上级部门Id不能为空")
    private String parentId;

    @ApiModelProperty(value = "排序字段,支持小数")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "部门编码",required = true)
    @NotBlank(message = "部门编码不能为空")
    private String deptCode;

    @ApiModelProperty(value = "部门名称",required = true)
    @NotBlank(message = "部门名称不能为空")
    private String deptName;

    @ApiModelProperty(value = "部门联系人")
    private String contact;

    @ApiModelProperty(value = "部门联系人手机号")
    private String contactPhone;

    @ApiModelProperty(value = "部门描述")
    private String description;

    @ApiModelProperty(value = "部门联系人微信")
    private String contactWx;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "部门联系人邮箱")
    private String contactEmail;

}
