package com.monkeyzi.oauth.web.dept;

import com.google.common.base.Preconditions;
import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.annotation.ValidateAnnotation;
import com.monkeyzi.oauth.base.controller.BaseController;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.domain.Department;
import com.monkeyzi.oauth.entity.dto.dept.DeptDto;
import com.monkeyzi.oauth.entity.dto.tree.TreeDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.service.DepartMentService;
import com.monkeyzi.oauth.utils.PublicUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @ApiOperation(httpMethod = "POST",value = "新增部门")
    @ValidateAnnotation
    public R addDept(@RequestBody @Valid  DeptDto deptDto, BindingResult bindingResult){
        log.info("新增部门的参数为 deptDto={}",deptDto);
        int result=departMentService.addDept(deptDto,getLoginAuthUser());
        return super.handleResult(result);
    }


    @PutMapping(value = "/editDept")
    @LogAnnotation
    @ApiOperation(httpMethod = "PUT",value = "修改部门")
    @ValidateAnnotation
    public R editDept(@RequestBody @Valid  DeptDto deptDto, BindingResult bindingResult){
        log.info("修改部门的参数为 deptDto={}",deptDto);
        Preconditions.checkArgument(StringUtils.isNotBlank(deptDto.getId()),ErrorCodeEnum.DS203.getMsg());
        int result=departMentService.editDept(deptDto,getLoginAuthUser());
        return super.handleResult(result);
    }



    @DeleteMapping(value = "/deleteDept/{ids}")
    @LogAnnotation
    @ApiOperation(httpMethod = "DELETE",value = "删除部门")
    public R deleteDept(@PathVariable List<String> ids){
        log.info("删除部门的参数为 ids={}",ids);
        Preconditions.checkArgument(PublicUtil.isNotEmpty(ids),ErrorCodeEnum.DS203.getMsg());
        departMentService.deleteDept(ids);
        return R.okMsg("操作成功");
    }


    @PostMapping(value = "/getAllDept")
    @LogAnnotation
    @ApiOperation(httpMethod = "POST",value = "查询部门树")
    public R getAllDept(){
        List<TreeDto> list=departMentService.queryDept();
        return R.ok("查询成功",list);
    }


    @GetMapping(value = "/getDeptDetail/{id}")
    @LogAnnotation
    @ApiOperation(httpMethod = "GET",value = "查询部门详情")
    public R getDeptDetail(@PathVariable String id){
        log.info("查询部门详情的参数为 id={}",id);
        Preconditions.checkArgument(StringUtils.isNoneBlank(id),ErrorCodeEnum.DS203.getMsg());
        Department department=departMentService.queryDeptDetail(id);
        return R.ok("查询成功",department);
    }


}
