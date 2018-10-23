package com.monkeyzi.oauth.web.dept;

import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.base.controller.BaseController;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.service.DepartMentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/dept", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "deptController", description = "部门管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DeptController extends BaseController {

    @Autowired
    private DepartMentService departMentService;


    @PostMapping(value = "/addDept")
    @LogAnnotation
    @ApiOperation(httpMethod = "POST",value = "新增/修改部门")
    public R addDept(){
        return R.ok();
    }


    @PostMapping(value = "/deleteDept")
    @LogAnnotation
    @ApiOperation(httpMethod = "POST",value = "删除部门")
    public R deleteDept(@RequestBody List<String> ids){
        return R.ok();
    }


    @PostMapping(value = "/getAllDept")
    @LogAnnotation
    @ApiOperation(httpMethod = "POST",value = "查询部门树")
    public R getAllDept(){
        return R.ok();
    }

}
