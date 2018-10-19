package com.monkeyzi.oauth.entity.domain;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "m_department")
@ApiModel(value = "部门")
@EqualsAndHashCode(callSuper = true)
public class Department extends BaseEntity implements Serializable {

    private Integer status;

    private String parentId;

    private BigDecimal sortOrder;

    private String deptCode;

    private String deptName;

    private Integer isParent;

    private String contact;

    private String contactPhone;

    private String descrption;

    private String contactWx;

    private Integer level;

    private Integer leaf;

    private String contactEmail;


}
