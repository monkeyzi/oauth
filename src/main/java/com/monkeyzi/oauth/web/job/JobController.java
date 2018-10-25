package com.monkeyzi.oauth.web.job;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.annotation.RateLimiter;
import com.monkeyzi.oauth.annotation.ValidateAnnotation;
import com.monkeyzi.oauth.base.BaseQueryDto;
import com.monkeyzi.oauth.base.controller.BaseController;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.domain.QuartzJob;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.service.QuartJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/job", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "JobController", description = "定时任务管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class JobController  extends BaseController {

    @Autowired
    private QuartJobService quartJobService;

    @ResponseBody
    @RequestMapping(value = "/tt")
    @RateLimiter(limit =3)
    public R dd(){
        throw new BusinessException(ErrorCodeEnum.RS304);
        //return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/t2")
    public R t(){
        return R.ok();
    }


    @PostMapping(value = "/queryJobWithPage")
    @ApiOperation(httpMethod = "POST",value = "分页查询定时任务列表")
    public R queryJobWithPage(@RequestBody BaseQueryDto queryDto){
        PageInfo pageInfo=quartJobService.queryJobWithPage(queryDto);
        return R.ok("查询成功",pageInfo);
    }


    @PostMapping(value = "/addJob")
    @LogAnnotation
    @ValidateAnnotation
    @ApiOperation(httpMethod = "POST",value = "新增定时任务")
    public R addJob(@RequestBody @Valid QuartzJob quartzJob, BindingResult bindingResult){
        log.info("添加定时任务的参数为  quartJob={}",quartzJob);
        int result=quartJobService.addJob(quartzJob,getLoginAuthUser());
        return super.handleResult(result);
    }



    @PostMapping(value = "/editJob")
    @LogAnnotation
    @ValidateAnnotation
    @ApiOperation(httpMethod = "POST",value = "更新定时任务")
    public R editJob(@RequestBody @Valid QuartzJob quartzJob, BindingResult bindingResult){

        log.info("更新定时任务的参数为 quartzJob={}",quartzJob);
        int  result=quartJobService.updateJob(quartzJob,getLoginAuthUser());
        return super.handleResult(result);
    }



    @PostMapping(value = "/pauseJob")
    @LogAnnotation
    @ApiOperation(httpMethod = "POST",value = "暂停定时任务")
    public R pauseJob(@RequestBody @Valid QuartzJob quartzJob, BindingResult bindingResult){

        log.info("暂停定时任务的参数为 quartzJob={}",quartzJob);

        int  result=quartJobService.pauseJob(quartzJob,getLoginAuthUser());
        return super.handleResult(result);
    }


    @PostMapping(value = "/runJob")
    @LogAnnotation
    @ApiOperation(httpMethod = "POST",value = "执行定时任务")
    public R runJob(@RequestBody QuartzJob quartzJob){

        log.info("暂停定时任务的参数为 quartzJob={}",quartzJob);
        int  result=quartJobService.runJob(quartzJob,getLoginAuthUser());
        return super.handleResult(result,"恢复执行定时任务成功");
    }


    @PostMapping(value = "/deleteJob")
    @LogAnnotation
    @ApiOperation(httpMethod = "POST",value = "删除定时任务")
    public R deleteJob(@RequestBody List<String> ids){

        log.info("删除定时任务的参数为 ids={}",ids);
        quartJobService.deleteJob(ids);
        return R.okMsg("删除定时任务成功");
    }


}
