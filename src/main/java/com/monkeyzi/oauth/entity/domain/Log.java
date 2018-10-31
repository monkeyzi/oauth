package com.monkeyzi.oauth.entity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.monkeyzi.oauth.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "日志")
@Table(name = "m_log")
public class Log extends BaseEntity {

    /**
     * 部门Id
     */
    @ApiModelProperty(value = "部门ID")
    private String deptId;
    /**
     * 部门名
     */
    @ApiModelProperty(value = "部门名")
    private String deptName;
    /**
     * 权限Id
     */
    @ApiModelProperty(value = "权限ID")
    private String permissionId;
    /**
     * 权限名
     */
    @ApiModelProperty(value = "权限名")
    private String permissionName;
    /**
     * 日志类型
     */
    @ApiModelProperty(value = "日志类型")
    private String logType;
    /**
     * 日志名
     */
    @ApiModelProperty(value = "日志名")
    private String logName;
    /**
     * 操作系统
     */
    @ApiModelProperty(value = "请求的操作")
    private String os;
    /**
     * 浏览器
     */
    @ApiModelProperty(value = "浏览器")
    private String browser;
    /**
     * ip
     */
    @ApiModelProperty(value = "ip")
    private String ip;
    /**
     * 位置
     */
    @ApiModelProperty(value = "地理位置")
    private String location;
    /**
     * 物理地址
     */
    @ApiModelProperty(value = "mac地址")
    private String mac;
    /**
     * 描述
     */
    @ApiModelProperty(value = "方法描述")
    private String description;
    /**
     * 请求数据
     */
    @ApiModelProperty(value = "请求数据")
    private String requestData;
    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求的url")
    private String requestUrl;
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应的数据")
    private String responseData;

    /**
     * 请求的类型
     */
    @ApiModelProperty(value = "请求类型")
    private String requestType;
    /**
     * 类名
     */
    @ApiModelProperty(value = "类名")
    private String className;
    /**
     * 方法名
     */
    @ApiModelProperty(value = "方法名")
    private String methodName;
    /**
     * 请求开始时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "请求开始时间")
    private Date startTime;
    /**
     * 请求结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "请求结束时间")
    private Date   endTime;
    /**
     * 请求耗时
     */
    @ApiModelProperty(value = "请求的时长")
    private Long excuteTime;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态  0可用  1不可用")
    private Integer status;
}
