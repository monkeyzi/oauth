package com.monkeyzi.oauth.utils;

import cn.hutool.core.lang.UUID;

public class FileUtils {

    /**
     * 根据文件的原始名获取到文件的后缀
     * @param originName 文件的原始名称
     * @return
     */
    public static String  getFileExtension(String originName){
        String extName = originName.substring(originName.lastIndexOf(".")+1);
        return extName;
    }

    /**
     * 文件重命名
     * @param originName
     * @return
     */
    public static String  rnFileName(String originName){
        String extName = originName.substring(originName.lastIndexOf("."));
        String reName= UUID.randomUUID().toString()+extName;
        return reName;
    }



}
