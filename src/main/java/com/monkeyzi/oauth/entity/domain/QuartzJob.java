package com.monkeyzi.oauth.entity.domain;

import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Data
@Table(name="m_quartz_job")
@EqualsAndHashCode(callSuper = true)
public class QuartzJob  extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "任务类名不能为空")
    @ApiModelProperty(value = "任务类名",required = true)
    private String jobClassName;

    @NotBlank(message = "任务表达式不能为空")
    @ApiModelProperty(value = "cron表达式",required = true)
    private String cronExpression;

    @ApiModelProperty(value = "参数")
    private String parameter;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "状态 0正常 -1停止")
    private Integer status;
}
