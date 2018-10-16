package com.monkeyzi.oauth.web.auth;

import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.annotation.ValidateAnnotation;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.domain.Permission;
import com.monkeyzi.oauth.entity.dto.LogDto;
import com.monkeyzi.oauth.entity.dto.gaode.GaodeLocation;
import com.monkeyzi.oauth.entity.param.UserDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.service.LogService;
import com.monkeyzi.oauth.service.PermissionService;
import com.monkeyzi.oauth.utils.GaodeUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class RestAuthController {
    @Autowired
    private LogService logService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private TaskExecutor taskExecutor;

    @Autowired
    private PermissionService permissionService;

    /**
     * 请求需要登录的接口
     * @return
     */
    @RequestMapping(value = "/needLogin")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R needLogin(){
       log.info("需要登录");
       return R.error(ErrorCodeEnum.GL401.getCode(),ErrorCodeEnum.GL401.getMsg());
    }


    @RequestMapping(value = "/test")
    @ApiOperation(httpMethod="POST",value ="测试" )
    @LogAnnotation
    @ValidateAnnotation
    public R test(@RequestBody @Valid UserDto userDto, BindingResult bindingResult){
        log.info("测试接口传过来的参数为  userDto={}",userDto);
        GaodeLocation location=GaodeUtils.getLocationByIpAddr("218.29.118.18");
        System.out.println(location);
        Permission permission=permissionService.selectByKey("11");
        taskExecutor.execute(()->{
            String name=Thread.currentThread().getName();
            System.out.println(name);
            log.info("我是异步任务");
        });
        return R.ok("ok",permission);
    }


    @PostMapping(value = "/saveLog")
    @ApiOperation(httpMethod = "POST", value = "记录操作日志")
    public Integer saveLog(@RequestBody LogDto logDto) {
        log.info("saveLog - 保存操作日志. logDto={}", logDto);
        return logService.saveLog(logDto);
    }
}
