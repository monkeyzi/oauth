package com.monkeyzi.oauth.service.impl;

import com.google.common.base.Preconditions;
import com.monkeyzi.oauth.base.service.BaseServiceImpl;
import com.monkeyzi.oauth.entity.domain.Log;
import com.monkeyzi.oauth.entity.domain.Permission;
import com.monkeyzi.oauth.entity.dto.LogDto;
import com.monkeyzi.oauth.mapper.LogMapper;
import com.monkeyzi.oauth.service.CommonService;
import com.monkeyzi.oauth.service.LogService;
import com.monkeyzi.oauth.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class LogServiceImpl extends BaseServiceImpl<Log>  implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private CommonService commonService;


    @Override
    public int saveLog(LogDto logDto) {
        if (logDto==null){
            log.error("记录日志时参数为空");
            Preconditions.checkArgument(logDto!=null,"参数为空");
        }
        //根据请求的路径查询权限信息
        Permission permission=permissionService.matchUrl(logDto.getRequestUrl());
        if (permission!=null){
           logDto.setPermissionId(permission.getId());
           logDto.setPermissionName(permission.getName());
        }
        //类型转换
        ModelMapper modelMapper=new ModelMapper();
        Log  mLog=modelMapper.map(logDto,Log.class);
        //地理位置数据查询
        String location=commonService.getAddressLocationByIp(logDto.getIp());
        mLog.setLocation(location);
        return logMapper.insertSelective(mLog);
    }
}
