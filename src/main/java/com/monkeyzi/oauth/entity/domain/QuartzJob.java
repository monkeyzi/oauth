package com.monkeyzi.oauth.entity.domain;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;


@Data
@Table(name="m_quartz_job")
@EqualsAndHashCode(callSuper = true)
public class QuartzJob  extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务类名")
    private String jobClassName;

    @ApiModelProperty(value = "cron表达式")
    private String cronExpression;

    @ApiModelProperty(value = "参数")
    private String parameter;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "状态 0正常 -1停止")
    private Integer status;
}
