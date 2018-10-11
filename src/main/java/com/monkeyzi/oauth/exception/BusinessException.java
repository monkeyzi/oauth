package com.monkeyzi.oauth.exception;

import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: 高yg
 * @date: 2018/10/11 22:12
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:系统业务异常
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {
    private int code;
    public BusinessException(){}
    public BusinessException(Throwable t){super(t);}
    public BusinessException(String msg){super(msg);}
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

    public BusinessException(ErrorCodeEnum codeEnum, Object... args) {
        super(String.format(codeEnum.getMsg(), args));
        this.code = codeEnum.getCode();
    }

}
