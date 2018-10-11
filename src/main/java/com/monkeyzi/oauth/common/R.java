package com.monkeyzi.oauth.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: 高yg
 * @date: 2018/7/21 20:45
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Getter
@Setter
public class R<T>  implements Serializable {
    private Integer code;
    private String  msg;
    private T       data;

    /**
     * 私有构造器
     */
    private R(Integer code){
        this.code=code;
    }

    private R(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }

    private R(Integer code, T data){
        this.code=code;
        this.data=data;
    }

    private R(Integer code, String msg, T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }


    /**
     * success 成功的方法
     */

    public boolean isSuccess(){
       return this.code.equals(ResponseCode.SUCCESS.getCode());
    }

    public static <T> R<T> ok(){
        return  new R<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> R<T> okMsg(String msg){
        return new R<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> R<T> ok(String msg,T data){
        return new R<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> R<T> ok(T data){
        return new R<T>(ResponseCode.SUCCESS.getCode(),data);
    }


    /**
     * error失败的消息
     */
    public static <T> R<T> error(){
        return new R<T>(ResponseCode.ERROR.getCode());
    }

    public static <T> R<T> errorMsg(String msg){
        return new R<T>(ResponseCode.ERROR.getCode(),msg);
    }

    public static <T> R<T> error(T data){
        return new R<T>(ResponseCode.ERROR.getCode(),data);
    }

    public static <T> R<T> error(String msg,T data){
        return new R<T>(ResponseCode.ERROR.getCode(),msg,data);
    }

    public static <T> R<T> error(Integer code,String msg){
        return new R<T>(code,msg);
    }
}
