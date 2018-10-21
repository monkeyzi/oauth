package com.monkeyzi.oauth.web.user;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.base.controller.BaseController;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.roleuser.BindUserRolesDto;
import com.monkeyzi.oauth.entity.dto.user.UserEditDto;
import com.monkeyzi.oauth.entity.dto.user.UserQueryDto;
import com.monkeyzi.oauth.entity.vo.roleuser.BindRoleVo;
import com.monkeyzi.oauth.enums.UserStatusEnum;
import com.monkeyzi.oauth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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


    @PostMapping(value = "/saveUser")
    @ApiOperation(httpMethod = "POST",value = "新增用户")
    @LogAnnotation
    public R  saveUser(@ApiParam(name = "userEditDto",value = "新增用户参数") @RequestBody @Valid UserEditDto userEditDto,
                       BindingResult bindingResult){
        log.info("新增用户的参数为  userEditDto={}",userEditDto);
        LoginAuthDto loginAuthDto=getLoginAuthUser();
        int result= userService.addUser(loginAuthDto,userEditDto);
        return super.handleResult(result);
    }


    @PostMapping(value = "/editUser")
    @ApiOperation(httpMethod = "POST",value = "修改用户")
    @LogAnnotation
    public R  editUser(@ApiParam(name = "userEditDto",value = "修改用户参数") @RequestBody @Valid
                                   UserEditDto userEditDto, BindingResult bindingResult){
        log.info("修改用户的参数为  userEditDto={}",userEditDto);
        LoginAuthDto loginAuthDto=getLoginAuthUser();
        int result=userService.editUser(loginAuthDto,userEditDto);
        return super.handleResult(result);
    }


    @PostMapping(value = "/disable/{userId}")
    @ApiOperation(httpMethod = "POST",value = "禁用用户")
    @LogAnnotation
    public R disableUser(@PathVariable String  userId){
        log.info("禁用用户参数为 userId={}",userId);
        int result=userService.modifyUserStatus(userId,getLoginAuthUser(),
                UserStatusEnum.DISABLE_USER.code);
        return super.handleResult(result);
    }



    @PostMapping(value = "/enable/{userId}")
    @ApiOperation(httpMethod = "POST",value = "启用/恢复用户")
    @LogAnnotation
    public R enableUser(@PathVariable String  userId){
        log.info("启用用户参数为 userId={}",userId);
        int result=userService.modifyUserStatus(userId,getLoginAuthUser(),
                UserStatusEnum.ENABLE_USER.code);
        return super.handleResult(result);
    }

    @PostMapping(value = "/deleteUser")
    @ApiOperation(httpMethod = "POST",value = "删除/批量删除用户")
    @LogAnnotation
    public R deleteUser(@ApiParam(name = "ids",value = "用户的Ids集合")  List<String> ids){
        log.info("删除用户的参数为 userIds=",ids);
        LoginAuthDto loginAuthDto=getLoginAuthUser();
        userService.deleteUserByUserIds(ids,loginAuthDto);
        return R.okMsg("删除成功");
    }


    @PostMapping(value = "/getBindRole/{userId}")
    @ApiOperation(httpMethod = "POST",value = "获取用户已经绑定的角色列表")
    public R getBindRole(@ApiParam(name = "userId",value = "用户Id")@PathVariable String userId){
        log.info("获取用户绑定的角色参数 userId={}",userId);
        LoginAuthDto loginAuthDto=getLoginAuthUser();
        BindRoleVo bindRoleVo=userService.getBindUserRoleList(userId,loginAuthDto);
        return R.ok("查询成功",bindRoleVo);
    }

    @PostMapping(value = "/bindUserRole")
    @ApiOperation(httpMethod = "POST",value = "用户绑定角色")
    @LogAnnotation
    public R bindUserRole(@ApiParam(name = "bindUserRolesDto",value = "用户绑定角色参数")@RequestBody BindUserRolesDto bindUserRolesDto){
        log.info("用户绑定角色的参数为 bindUserRolesDto=",bindUserRolesDto);
        LoginAuthDto loginAuthDto=getLoginAuthUser();
        userService.bindUserRole(bindUserRolesDto,loginAuthDto);
        return R.okMsg("操作成功");
    }












}
