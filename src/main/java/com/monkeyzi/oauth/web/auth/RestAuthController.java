package com.monkeyzi.oauth.web.auth;

import com.monkeyzi.oauth.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class RestAuthController {

    @RequestMapping(value = "/needLogin")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R needLogin(){
       log.info("需要登录");
       return R.error(401,"请登录系统");
    }


    @RequestMapping(value = "/test")
    public R test(){
        return R.ok("ok","hhhhh");
    }
}
