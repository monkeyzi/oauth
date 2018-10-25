package com.monkeyzi.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.base.BaseQueryDto;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.QuartzJob;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.enums.ErrorCodeEnum;
import com.monkeyzi.oauth.enums.JobStatusEnum;
import com.monkeyzi.oauth.exception.BusinessException;
import com.monkeyzi.oauth.service.QuartJobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class QuartJobServiceImpl extends BaseServiceImpl<QuartzJob> implements QuartJobService {
    @Autowired
    private Scheduler scheduler;



    @Override
    public int addJob(QuartzJob quartzJob, LoginAuthDto loginAuthDto) {
        //查询任务类名是否存在
        QuartzJob quartzJob1=new QuartzJob();
        quartzJob1.setJobClassName(quartzJob.getJobClassName());
        List<QuartzJob> quartzJobs=this.select(quartzJob1);
        if (quartzJobs!=null&&quartzJobs.size()>0){
            throw new BusinessException(ErrorCodeEnum.JS401);
        }
        // 创建任务
        add(quartzJob.getJobClassName(),quartzJob.getCronExpression(),quartzJob.getParameter());
        quartzJob.setUpdateInfo(loginAuthDto);
        quartzJob.setId(generateId());
        // 保存任务到数据库
        int result=super.saveSelective(quartzJob);
        return result;
    }



    @Override
    public int updateJob(QuartzJob quartzJob, LoginAuthDto loginAuthUser) {
        QuartzJob job=super.selectByKey(quartzJob.getId());
        if (job==null){
            throw  new BusinessException(ErrorCodeEnum.JS406);
        }
        // 先从内存中删除该定时任务
        delete(quartzJob.getJobClassName());
        // 添加新修改的任务到内存中
        add(quartzJob.getJobClassName(),quartzJob.getCronExpression(),quartzJob.getParameter());
        // 执行更新
        quartzJob.setUpdateInfo(loginAuthUser);
        quartzJob.setStatus(JobStatusEnum.JOB_STATUS_RUN.code);
        int result=super.updateByPrimaryKey(quartzJob);
        return result;
    }

    @Override
    public PageInfo queryJobWithPage(BaseQueryDto queryDto) {
        PageHelper.startPage(queryDto.getPageNum(),queryDto.getPageSize());
        PageHelper.orderBy(" create_time desc");
        List<QuartzJob> list=super.selectAll();
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int pauseJob(QuartzJob quartzJob, LoginAuthDto loginAuthUser) {
        if (StringUtils.isNotBlank(quartzJob.getId())){
            throw new BusinessException(ErrorCodeEnum.JS405);
        }
        QuartzJob job=super.selectByKey(quartzJob.getId());
        if (job==null){
            throw  new BusinessException(ErrorCodeEnum.JS406);
        }
        try {
            scheduler.pauseJob(JobKey.jobKey(job.getJobClassName()));
        }catch (SchedulerException e){
            log.error("暂定定时任务失败了 e={}",e.toString());
            throw  new BusinessException(ErrorCodeEnum.JS407);
        }
        job.setStatus(JobStatusEnum.JOB_STATUS_STOP.code);
        int result=super.updateByPrimaryKey(job);
        return result;
    }

    @Override
    public int runJob(QuartzJob quartzJob, LoginAuthDto loginAuthUser) {
        if (StringUtils.isNotBlank(quartzJob.getId())){
            throw new BusinessException(ErrorCodeEnum.JS405);
        }
        QuartzJob job=super.selectByKey(quartzJob.getId());
        if (job==null){
            throw  new BusinessException(ErrorCodeEnum.JS406);
        }
        try {
            scheduler.resumeJob(JobKey.jobKey(job.getJobClassName()));
        }catch (SchedulerException e){
            log.error("恢复执行定时任务失败了 e={}",e.toString());
            throw  new BusinessException(ErrorCodeEnum.JS407);
        }
        job.setStatus(JobStatusEnum.JOB_STATUS_RUN.code);
        int result=super.updateByPrimaryKey(job);
        return result;
    }



    @Override
    public void deleteJob(List<String> ids) {
         ids.forEach(a->{
             QuartzJob job=super.selectByKey(a);
             delete(job.getJobClassName());
             super.deleteByPrimaryKey(a);
         });
    }


    /**
     * 添加 quartJob任务
     * @param jobClassName 任务类名
     * @param cronExpression 任务表达式
     * @param parameter  任务参数
     */
   private void add(String jobClassName, String cronExpression, String parameter){
        try {
            // 启动 QuartJob任务启动器
            scheduler.start();
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
                    .withIdentity(jobClassName)
                    .usingJobData("parameter",parameter)
                    .build();
            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName)
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);

        }catch (SchedulerException e1){
            log.error("定时任务创建失败", e1.toString());
            throw  new BusinessException(ErrorCodeEnum.JS402);
        }catch (Exception e){
            log.error("后台根据类名找不到任务类",e.toString());
            throw  new BusinessException(ErrorCodeEnum.JS403);
        }
   }

    /**
     * 删除内存中的job
     * @param jobClassName
     */
   private void delete(String jobClassName){
       try {

           scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName));
           scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName));
           scheduler.deleteJob(JobKey.jobKey(jobClassName));
       }catch (Exception e){
           log.error("删除定时任务 失败了 e={}",e.toString());
           throw new BusinessException(ErrorCodeEnum.JS404);
       }

   }

    /**
     * 通过类名获取类
     * @param className
     * @return
     * @throws Exception
     */
    private Job  getClass(String className) throws Exception {
       Class<?> class1=Class.forName(className);
       return (Job) class1.newInstance();
    }
}
