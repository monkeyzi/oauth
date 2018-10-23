package com.monkeyzi.oauth.web.permission;

import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.annotation.ValidateAnnotation;
import com.monkeyzi.oauth.base.controller.BaseController;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.permission.PermissionDto;
import com.monkeyzi.oauth.security.permission.MySecurityMetadataSource;
import com.monkeyzi.oauth.service.PermissionService;
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
 * @author: 高yg
 * @date: 2018/10/22 20:51
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Slf4j
@RestController
@RequestMapping(value = "/auth/permission", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "permissionController", description = "菜单/权限管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;


    @PostMapping(value = "/getAllPermissionListWithRoleId/{roleId}")
    @ApiOperation(httpMethod = "POST",value = "查询角色的菜单权限列表")
    public R getAllPermissionListWithRoleId(@PathVariable String roleId){
        LoginAuthDto loginAuthDto=super.getLoginAuthUser();
        List<PermissionDto> permissionDtoList=permissionService.getAllPermissionListByRoleId(roleId,loginAuthDto);
        return R.ok("查询成功",permissionDtoList);
    }


    @PostMapping(value = "/getAllPermissionList")
    @ApiOperation(httpMethod = "POST",value = "查询系统所有的菜单权限列表")
    public R getAllPermissionList(){
        List<PermissionDto> permissionDtoList=permissionService.getAllPermissionList();
        return R.ok("查询成功",permissionDtoList);
    }


    @PostMapping(value = "/addPermission")
    @ApiOperation(httpMethod = "POST",value = "添加/修改菜单权限")
    @LogAnnotation
    @ValidateAnnotation
    public R addPermission(@ApiParam(name = "permissionDto",value = "新增/修改菜单权限参数") @RequestBody @Valid PermissionDto permissionDto, BindingResult bindingResult){
        log.info("新增菜单权限参数 permissionDto={}",permissionDto);
        int result=permissionService.addPermission(permissionDto,getLoginAuthUser());
        //重新加载权限
        mySecurityMetadataSource.loadPermissionResources();
        return super.handleResult(result);
    }


    @PostMapping(value = "/deletePermission")
    @ApiOperation(httpMethod = "POST",value = "删除菜单权限")
    @LogAnnotation
    @ValidateAnnotation
    public R deletePermission(@RequestBody List<String> ids){
        log.info("删除菜单权限的参数为 ids={}",ids);
        permissionService.deletePermission(ids);
        //重新加载权限
        mySecurityMetadataSource.loadPermissionResources();
        return R.okMsg("操作成功");
    }





}
