package com.monkeyzi.oauth.service.impl;

import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.Log;
import com.monkeyzi.oauth.entity.dto.LogDto;
import com.monkeyzi.oauth.mapper.LogMapper;
import com.monkeyzi.oauth.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogServiceImpl extends BaseServiceImpl<Log>  implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public int saveLog(LogDto logDto) {
        log.info("执行ssss-------------------");
        return 0;
    }
}
