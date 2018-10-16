package com.monkeyzi.oauth.service;

public interface CommonService {
    /**
     * 根据ip获取位置信息
     * @param ip
     * @return
     */
  String getAddressLocationByIp(String ip);
}
