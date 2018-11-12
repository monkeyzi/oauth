package com.monkeyzi.oauth.enums;

public enum  CommStatuEnum {

    DEPT_STATUS_RUN(0,"正常状态"),

    DEPT_STATUS_STOP(1,"禁用状态");

    public Integer code;

    private String msg;

    CommStatuEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
