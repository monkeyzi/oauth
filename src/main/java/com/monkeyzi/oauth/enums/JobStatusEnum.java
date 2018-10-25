package com.monkeyzi.oauth.enums;

import lombok.Getter;

@Getter
public enum  JobStatusEnum {

    JOB_STATUS_RUN(0,"正常状态"),

    JOB_STATUS_STOP(1,"停止状态");

    public Integer code;

    private String msg;

    JobStatusEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
