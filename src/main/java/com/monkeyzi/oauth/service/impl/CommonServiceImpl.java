package com.monkeyzi.oauth.service.impl;

import com.google.common.base.Preconditions;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.dto.gaode.GaodeLocation;
import com.monkeyzi.oauth.service.CommonService;
import com.monkeyzi.oauth.utils.GaodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {


    @Override
    public String getAddressLocationByIp(String ip) {
        Preconditions.checkArgument(StringUtils.isNotBlank(ip),"ip不能为空");
        String temp="192.168.";
        String temp1="127.0.";
        if (ip.startsWith(temp)||ip.startsWith(temp1)){
            ip="59.110.171.71";
        }
        GaodeLocation location=GaodeUtils.getLocationByIpAddr(ip);
        if (location!=null){
            return location.getProvince().contains("市") ? location.getCity() :
                    location.getProvince() + GlobalConstant.Symbol.SHORT_LINE + location.getCity();
        }
        return null;
    }
}
