package com.monkeyzi.oauth.enums;

import lombok.Getter;

@Getter
public enum SysEnum {

    PERMISSION_CODE(1,"权限类型"),

    MENU_CODE(0,"页面操作");

    public Integer code;

    private String msg;

    SysEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
