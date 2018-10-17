package com.monkeyzi.oauth.base.controller;

import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.utils.PublicUtil;
import com.monkeyzi.oauth.utils.ThreadLocalMapUtils;
import lombok.extern.slf4j.Slf4j;
/**
 * @author 高艳国
 * @date 2018/10/17 17:52
 * @description BaseController
 **/
@Slf4j
public class BaseController {

    /**
     * 获取当前的登录的用户
     * @return
     */
    protected LoginAuthDto getLoginAuthUser(){
        LoginAuthDto loginAuthDto= (LoginAuthDto) ThreadLocalMapUtils.get(GlobalConstant.Sys.CURRENT_AUTH_USER);
        if (PublicUtil.isEmpty(loginAuthDto)){
            log.error("没有获取到当前的登录用户信息");
            throw new BusinessException(ErrorCodeEnum.GL401);
        }
        return loginAuthDto;
    }

    /**
     * 处理响应
     * @param result
     * @param <T>
     * @return
     */
    protected <T> R<T> handleResult(T result){
        boolean flag=isFlag(result);
        if (flag){
            return R.ok("操作成功",result);
        }else {
            return R.error("操作失败",null);
        }
    }

    /**
     * 处理响应
     * @param result
     * @param msg
     * @param <E>
     * @return
     */
    protected <E> R<E> handleResult(E result,String msg){
        boolean flag=isFlag(result);
        if (flag){
            return R.ok(msg,result);
        }else {
            return R.error(msg,null);
        }
    }

    private boolean isFlag(Object result) {
        boolean flag;
        if (result instanceof Integer) {
            flag = (Integer) result > 0;
        } else if (result instanceof Boolean) {
            flag = (Boolean) result;
        } else {
            flag = PublicUtil.isNotEmpty(result);
        }
        return flag;
    }




}
