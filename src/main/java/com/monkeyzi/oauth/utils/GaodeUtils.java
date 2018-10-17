package com.monkeyzi.oauth.utils;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkeyzi.oauth.entity.dto.gaode.GaodeLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 高德工具类
 */
@Slf4j
@Component
public class GaodeUtils {


    private static String key;

    public  String getKey() {
        return key;
    }

    @Value("${monkeyzi.gaode.key}")
    public  void setKey(String key) {
        GaodeUtils.key = key;
    }

    /**
     * 根据ip获取位置信息
     * @param ipAddr
     * @return
     */
    public static GaodeLocation  getLocationByIpAddr(String ipAddr){
      log.info("根据ip定位. ipAddr={}", ipAddr);
      GaodeLocation location = null;
      String urlAddressIp = "http://restapi.amap.com/v3/ip?key="+key+"&ip=%s";
      String url = String.format(urlAddressIp, ipAddr);
      try {
          String str = HttpClientUtil.get(HttpConfig.custom().url(url));
          location = new ObjectMapper().readValue(str, GaodeLocation.class);
      } catch (Exception e) {
          log.error("getLocationByIpAddr={}", e.getMessage(), e);
      }
      log.info("getLocationByIpAddr - 根据IP定位. ipAddr={}, location={}", ipAddr, location);
      return location;
    }



}
