package com.monkeyzi.oauth.web.user;

import com.monkeyzi.oauth.base.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author 高艳国
 * @date 2018/10/17 18:15
 * @description 用户管理
 **/
@RestController
@Slf4j
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "UserController", description = "用户管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController extends BaseController {


}
