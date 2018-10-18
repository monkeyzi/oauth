package com.monkeyzi.oauth.web.user;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.base.controller.BaseController;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.user.UserEditDto;
import com.monkeyzi.oauth.entity.dto.user.UserQueryDto;
import com.monkeyzi.oauth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 高艳国
 * @date 2018/10/17 18:15
 * @description 用户管理
 **/
@RestController
@Slf4j
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "UserController", description = "用户管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/queryUserListWithPage")
    @ApiOperation(httpMethod = "POST",value = "分页查询用户列表")
    public R queryUserListWithPage(@ApiParam(name = "userQueryDto",value = "分页查询用户参数") @RequestBody UserQueryDto userQueryDto){
        log.info("分页查询用户列表参数 userQueryDto={} ",userQueryDto);
        PageInfo pageInfo=userService.queryUserListWithPage(userQueryDto);
        return  R.ok("查询成功",pageInfo);
    }


    @PostMapping(value = "saveUser")
    @ApiOperation(httpMethod = "POST",value = "新增用户")
    @LogAnnotation
    public R  saveUser(@ApiParam(name = "userEditDto",value = "新增用户参数") @RequestBody @Valid UserEditDto userEditDto,
                       BindingResult bindingResult){
        log.info("新增用户的参数为  userEditDto={}",userEditDto);
        LoginAuthDto loginAuthDto=getLoginAuthUser();
        int result= userService.addUser(loginAuthDto,userEditDto);
        return super.handleResult(result);
    }


    @PostMapping(value = "editUser")
    @ApiOperation(httpMethod = "POST",value = "修改用户")
    @LogAnnotation
    public R  editUser(@ApiParam(name = "userEditDto",value = "修改用户参数") @RequestBody @Valid
                                   UserEditDto userEditDto, BindingResult bindingResult){
        log.info("修改用户的参数为  userEditDto={}",userEditDto);
        LoginAuthDto loginAuthDto=getLoginAuthUser();
        int result=userService.editUser(loginAuthDto,userEditDto);
        return super.handleResult(result);
    }



}
