package com.monkeyzi.oauth.web.role;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.annotation.ValidateAnnotation;
import com.monkeyzi.oauth.base.controller.BaseController;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.domain.Role;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.permission.BindPermissionDto;
import com.monkeyzi.oauth.entity.dto.role.RoleDefaultDto;
import com.monkeyzi.oauth.entity.dto.role.RoleDto;
import com.monkeyzi.oauth.entity.dto.role.RoleQueryDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.enums.RoleStatusEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.service.RolePermissionService;
import com.monkeyzi.oauth.service.RoleService;
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

@Slf4j
@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "RoleController", description = "角色管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;


    @PostMapping(value = "/addRole")
    @LogAnnotation
    @ValidateAnnotation
    @ApiOperation(httpMethod = "POST",value = "新增/修改角色")
    public R addRole(@ApiParam(name = "roleDto",value = "新增/修改角色")@Valid  @RequestBody RoleDto roleDto, BindingResult bindingResult){
        log.info("新增/修改角色的参数为 roleDto={}",roleDto);
        if (roleDto==null){
            throw new BusinessException(ErrorCodeEnum.GL10001);
        }
        LoginAuthDto loginAuthDto=getLoginAuthUser();
        int result=roleService.addRole(roleDto,loginAuthDto);
        return super.handleResult(result);
    }


    @PostMapping(value = "/getAllRoleList")
    @ApiOperation(httpMethod = "POST",value = "获取系统所有的可用角色列表")
    public R getAllRoleList(){
        List<Role> list=roleService.getAllRoleList();
        return R.ok("ok",list);
    }


    @PostMapping(value = "/getAllRoleListByPage")
    @ApiOperation(httpMethod = "POST",value = "分页获取系统所有的可用角色列表")
    public R getAllRoleListByPage(@RequestBody RoleQueryDto roleQueryDto){
        log.info("分页查询系统角色的参数为 roleQueryDto={}",roleQueryDto);
        PageInfo pageInfo =roleService.getAllRoleListWithPage(roleQueryDto);
        return R.ok("ok",pageInfo);
    }



    @PostMapping(value = "/setDefaultRole")
    @LogAnnotation
    @ValidateAnnotation
    @ApiOperation(httpMethod = "POST",value = "设置或者取消默认角色")
    public R setDefaultRole(@ApiParam(name = "roleDefaultDto",value = "设置或者取消默认角色")
                                @Valid  @RequestBody RoleDefaultDto roleDefaultDto, BindingResult bindingResult){
        log.info("设置或者取消默认角色的参数为 roleDefaultDto={}",roleDefaultDto);
        int result=roleService.setDefaultRole(roleDefaultDto,getLoginAuthUser());
        return super.handleResult(result);
    }


    @PostMapping(value = "/deleteRole")
    @LogAnnotation
    @ApiOperation(httpMethod = "POST",value = "删除角色")
    public R deleteRole(@RequestBody  List<String> ids){
        log.info("删除角色的参数为 ids={}",ids);
        roleService.deleteRole(ids,getLoginAuthUser());
        return R.okMsg("操作成功");
    }


    @PostMapping(value = "/disableRole")
    @LogAnnotation
    @ApiOperation(httpMethod = "POST",value = "禁用角色---支持批量")
    public R disableRole(@RequestBody  List<String> ids){
        log.info("禁用角色 ids={}",ids);
        roleService.disableRole(ids,getLoginAuthUser(),RoleStatusEnum.DISABLE_ROLE.code);
        return R.okMsg("操作成功");
    }

    @PostMapping(value = "/enableRole")
    @LogAnnotation
    @ApiOperation(httpMethod = "POST",value = "启用角色---支持批量")
    public R enableRole(@RequestBody  List<String> ids){
        log.info("禁用角色 ids={}",ids);
        roleService.disableRole(ids,getLoginAuthUser(),RoleStatusEnum.ENABLE_ROLE.code);
        return R.okMsg("操作成功");
    }


    @PostMapping(value = "/bindRolePermissions")
    @LogAnnotation
    @ValidateAnnotation
    @ApiOperation(httpMethod = "POST",value = "角色绑定权限")
    public R  bindRolePermissions(@ApiParam(name = "bindPermissionDto",value = "角色绑定权限")@Valid @RequestBody BindPermissionDto bindPermissionDto,BindingResult bindingResult){
         log.info("角色绑定权限的参数是 bindPermissionDto={}",bindingResult);
        rolePermissionService.bindRolePermissions(bindPermissionDto,getLoginAuthUser());
        return R.okMsg("操作成功");
    }






















}
