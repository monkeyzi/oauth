package com.monkeyzi.oauth.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author: 高yg
 * @date: 2018/10/9 22:18
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:响应json
 */
@Slf4j
public class ResponseJsonUtil {

    /**
     * 使用response输出json
     * @param response
     * @param map
     */
    public static void out(ServletResponse response, Map<String, Object> map){
        PrintWriter out=null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out=response.getWriter();
            out.println(new JSONObject(map));
        }catch (Exception e){
            log.error("response输出json异常 e={}",e);
        }finally {
            if (out!=null){
                out.flush();
                out.close();
            }
        }
    }

    public  static <T> Map<String,Object> map(boolean flag,Integer code,String msg,T data){
        Map<String,Object> map=Maps.newHashMap();
        map.put("success",flag);
        map.put("msg",msg);
        map.put("code",code);
        map.put("data",data);
        map.put("timestamp",System.currentTimeMillis());
        return map;
    }
}
