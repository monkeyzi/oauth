package com.monkeyzi.oauth.service;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.base.BaseQueryDto;
import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.QuartzJob;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;

import java.util.List;

public interface QuartJobService extends BaseService<QuartzJob> {

    /**
     * 新增任务
     * @param quartzJob
     * @param loginAuthDto
     * @return
     */
    int  addJob(QuartzJob quartzJob, LoginAuthDto loginAuthDto);

    /**
     * 更新定时任务
     * @param quartzJob
     * @param loginAuthUser
     * @return
     */
    int updateJob(QuartzJob quartzJob, LoginAuthDto loginAuthUser);

    /**
     * 分页查询定时任务列表
     * @param queryDto
     * @return
     */
    PageInfo queryJobWithPage(BaseQueryDto queryDto);

    /**
     * 暂停定时任务
     * @param quartzJob
     * @param loginAuthUser
     * @return
     */
    int pauseJob(QuartzJob quartzJob, LoginAuthDto loginAuthUser);

    /**
     * 恢复执行定时任务
     * @param quartzJob
     * @param loginAuthUser
     * @return
     */
    int runJob(QuartzJob quartzJob, LoginAuthDto loginAuthUser);

    /**
     * 删除定时任务
     * @param ids
     */
    void deleteJob(List<String> ids);
}
