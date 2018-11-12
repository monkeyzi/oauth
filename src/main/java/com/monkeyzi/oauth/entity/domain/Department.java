package com.monkeyzi.oauth.entity.domain;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "m_department")
@ApiModel(value = "部门")
@Alias(value = "department")
@EqualsAndHashCode(callSuper = true)
public class Department extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "状态 0：可用  1:不可用")
    private Integer status;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    @ApiModelProperty(value = "排序字段,支持小数")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "部门编码")
    private String deptCode;

    @ApiModelProperty(value = "部门名称")
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
