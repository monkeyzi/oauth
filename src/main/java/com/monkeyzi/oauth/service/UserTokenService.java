package com.monkeyzi.oauth.service;

import com.monkeyzi.oauth.base.service.BaseService;
import com.monkeyzi.oauth.entity.domain.UserToken;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;

public interface UserTokenService  extends BaseService<UserToken> {
    /**
     * 保存用户token信息
     * @param token
     * @param loginAuthDto
     * @param request
     * @return
     */
    int saveUserToken(OAuth2AccessToken token, LoginAuthDto loginAuthDto, HttpServletRequest request);
}
