package com.monkeyzi.oauth.exception;

import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {


    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R businessException(BusinessException e) {
        log.error("business exception");
        return R.error(e.getCode(), e.getMessage());
    }
    /**
     * 404
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public R NoHandlerFoundException(NoHandlerFoundException e){
        log.error("请求的接口资源不存在={}",e.getMessage(),e);
        return R.error(ErrorCodeEnum.GL404.getCode(),ErrorCodeEnum.GL404.getMsg());
    }
    /**
     * 参数异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R illegalArgumentException(IllegalArgumentException e) {
        log.error("参数异常={}",e.getMessage(),e );
        return R.error(ErrorCodeEnum.GL10001.getCode(),e.getMessage());
    }
    /**
     * 请求方法不支持的异常
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public R MethodNotSupportException(HttpRequestMethodNotSupportedException e){
        log.error("请求方法不支持 e={}",e);
        return R.error(ErrorCodeEnum.GL405.getCode(),ErrorCodeEnum.GL405.getMsg());
    }

    /**
     * 415
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public R httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("不支持的媒体类型 e={}",e);
        return R.error(ErrorCodeEnum.GL415.getCode(), ErrorCodeEnum.GL415.getMsg());
    }

    /**
     * bad request
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public R HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("坏的请求 e={}",e);
        return R.error(ErrorCodeEnum.GL400.getCode(), ErrorCodeEnum.GL400.getMsg());
    }

    /**
     * 500错误
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public R Exception(Exception e){
        log.error("e={}",e.getMessage());
        return R.error(ErrorCodeEnum.GL500.getCode(),e.getMessage());
    }
}
