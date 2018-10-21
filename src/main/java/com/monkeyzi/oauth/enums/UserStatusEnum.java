package com.monkeyzi.oauth.enums;

import com.monkeyzi.oauth.entity.domain.User;
import lombok.Getter;

/**
 * @author: 高yg
 * @date: 2018/10/21 18:45
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Getter
public enum UserStatusEnum {
    /**
     * 禁用用户
     */
    DISABLE_USER(3, "禁用用户"),
    ENABLE_USER(1,"正常用户");

    public Integer code;

    public String msg;

    UserStatusEnum(int code, String msg){
        this.code=code;
        this.msg=msg;
    }


}
