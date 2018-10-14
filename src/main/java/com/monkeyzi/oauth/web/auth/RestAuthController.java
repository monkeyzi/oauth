package com.monkeyzi.oauth.web.auth;

import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.Permission;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class RestAuthController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private TaskExecutor taskExecutor;

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
        System.out.println("111");
        Permission permission=permissionService.selectByKey("11");
        redisTemplate.opsForValue().set("高艳国","ghuoguo");
        String value= (String) redisTemplate.opsForValue().get("高艳国");
        System.out.println(value);

        taskExecutor.execute(()->{
            String name=Thread.currentThread().getName();
            System.out.println(name);
            log.info("我是异步任务");
        });
        return R.ok("ok",permission);
    }
}
