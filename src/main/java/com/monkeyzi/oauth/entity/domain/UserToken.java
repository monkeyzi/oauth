package com.monkeyzi.oauth.entity.domain;

import com.monkeyzi.oauth.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Table;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Alias(value = "userToken")
@Table(name = "m_user_token")
public class UserToken extends BaseEntity {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户编号
     */
    private String userId;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 浏览器类型
     */
    private String browser;
    /**
     * 登陆人Ip地址
     */
    private String loginIp;

    /**
     * 登录地址
     */
    private String loginLocation;

    /**
     * 登录时间
     */
    private Date loginTime;

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

    /**
     * 0 在线 10已刷新 20 离线
     */
    private Integer status;
}
