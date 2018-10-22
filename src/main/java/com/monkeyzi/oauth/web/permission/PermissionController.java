package com.monkeyzi.oauth.web.permission;

import com.monkeyzi.oauth.base.controller.BaseController;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.permission.PermissionDto;
import com.monkeyzi.oauth.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping(value = "/permission", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "permissionController", description = "菜单/权限管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

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
        LoginAuthDto loginAuthDto=super.getLoginAuthUser();
        List<PermissionDto> permissionDtoList=permissionService.getAllPermissionList();
        return R.ok("查询成功",permissionDtoList);
    }
}
