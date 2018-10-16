package com.monkeyzi.oauth.entity.dto;

import com.monkeyzi.oauth.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class LogDto extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 部门Id
     */
    private String deptId;
    /**
     * 部门名
     */
    private String deptName;
    /**
     * 权限Id
     */
    private String permissionId;
    /**
     * 权限名
     */
    private String permissionName;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 日志名
     */
    private String logName;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * ip
     */
    private String ip;
    /**
     * 位置
     */
    private String location;
    /**
     * 物理地址
     */
    private String mac;
    /**
     * 描述
     */
    private String description;
    /**
     * 请求数据
     */
    private String requestData;
    /**
     * 请求路径
     */
    private String requestUrl;
    /**
     * 响应路径
     */
    private String responseData;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 请求开始时间
     */
    private Date   startTime;
    /**
     * 请求结束时间
     */
    private Date   endTime;
    /**
     * 请求耗时
     */
    private Long excuteTime;
    /**
     * 状态
     */
    private Integer status;

}
