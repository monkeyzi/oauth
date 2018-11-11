package com.monkeyzi.oauth.entity.dto;

import lombok.Data;

/**
 * @author: 高yg
 * @date: 2018/11/10 13:12
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
public class UserTokenDto  extends LoginAuthDto{


    /**
     * 访问token
     */
    private String accessToken;

    /**
     * 刷新token
     */
    private String refreshToken;
    /**
     * token类型
     */
    private String tokenType;
    /**
     * 访问token的生效时间(秒)
     */
    private Integer accessTokenValidity;

    /**
     * 刷新token的生效时间(秒)
     */
    private Integer refreshTokenValidity;

}
