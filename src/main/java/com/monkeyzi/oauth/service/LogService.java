package com.monkeyzi.oauth.service;

import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.Log;
import com.monkeyzi.oauth.entity.dto.LogDto;

public interface LogService  extends BaseService<Log> {
    /**
     * 保存日志
     * @param logDto
     * @return
     */
    int  saveLog(LogDto logDto);
}
