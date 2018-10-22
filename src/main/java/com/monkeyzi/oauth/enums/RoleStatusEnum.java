package com.monkeyzi.oauth.enums;

import lombok.Getter;

/**
 *
 */
@Getter
public enum  RoleStatusEnum {
    /**
     * 系统角色状态
     */
    DISABLE_ROLE(1, "禁用状态"),
    ENABLE_ROLE(0,"正常状态"),

    DEFAULT_ROLE(1,"默认角色"),
    NOT_DEFAULT_ROLE(0,"非默认角色");
    public Integer code;

    public String msg;

    RoleStatusEnum(int code, String msg){
        this.code=code;
        this.msg=msg;
    }
}
