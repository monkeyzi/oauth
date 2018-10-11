package com.monkeyzi.oauth.common;

import lombok.Getter;

/**
 * @author: 高yg
 * @date: 2018/7/21 20:47
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Getter
public enum ResponseCode {


    SUCCESS(0,"成功"),
    ERROR(1,"服务异常");


    private final Integer code;
    private final String msg;


    ResponseCode(Integer code, String msg){
          this.code=code;
          this.msg=msg;
    }




}
