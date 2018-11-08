package com.monkeyzi.oauth.enums;

import com.monkeyzi.oauth.common.GlobalConstant;
import lombok.Getter;

@Getter
public enum  FileSaveEnum {

    ALI_OSS_ENUM(GlobalConstant.Oss.ALI_OSS,"阿里云"),

    QINIU_OSS_ENUM(GlobalConstant.Oss.QINIU_OSS,"七牛云");

    public  String code;

    public String msg;

    FileSaveEnum(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
