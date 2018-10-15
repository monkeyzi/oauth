package com.monkeyzi.oauth.utils;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 高yg
 * @date: 2018/10/15 22:53
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class ObjectUtil {

    public static String mapToString(Map<String, String[]> paramMap){

        if (paramMap == null) {
            return "";
        }
        Map<String, Object> params = new HashMap<>(16);
        paramMap.forEach((k,v)->{
            String value=(v!=null&&v.length>0)?v[0]:"";
            String obj=StrUtil.endWithIgnoreCase(k, "password") ? "*******" : value;
            params.put(k,obj);
        });
        return new Gson().toJson(params);
    }
}
