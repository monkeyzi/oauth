package com.monkeyzi.oauth.web.auth;

import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.Permission;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class RestAuthController {

    @Autowired
    private PermissionService permissionService;
    @RequestMapping(value = "/needLogin")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R needLogin(){
       log.info("需要登录");
        System.out.println("1111");
       return R.error(ErrorCodeEnum.GL401.getCode(),ErrorCodeEnum.GL401.getMsg());
    }


    @RequestMapping(value = "/test")
    public R test(){
        //Preconditions.checkArgument(false,"hhhhh");
        System.out.println("111");
        Permission permission=permissionService.selectByKey("11");

        return R.ok("ok",permission);
    }
}
