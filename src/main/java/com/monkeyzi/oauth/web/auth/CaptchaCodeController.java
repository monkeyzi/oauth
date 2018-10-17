package com.monkeyzi.oauth.web.auth;

import com.monkeyzi.oauth.base.controller.BaseController;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.dto.captcha.CaptchaCode;
import com.monkeyzi.oauth.utils.CaptchaCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: 高yg
 * @date: 2018/10/17 22:22
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:图形验证码
 */
@RestController
@Slf4j
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "CaptchaCodeController", description = "图形验证码", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CaptchaCodeController extends BaseController {

    @Resource
    private RedisTemplate redisTemplate;


    @RequestMapping(value = "/initCode")
    @ApiOperation(httpMethod = "GET",value = "初始化验证码")
    public R  initCode(){
      //生成随机验证码
      String code=new CaptchaCodeUtil().randomStr(4);
      String captchaId=UUID.randomUUID().toString().replace("-","");
      //将id和code存到redis  有效期3分钟
      redisTemplate.opsForValue().set(captchaId,code,3L,TimeUnit.MINUTES);
      CaptchaCode captchaCode=new CaptchaCode();
      captchaCode.setCaptchaId(captchaId);
      return R.ok("成功",captchaCode);
    }

    @RequestMapping(value = "/draw/{captchaId}")
    @ApiOperation(httpMethod = "GET",value = "根据验证码Id生成图片验证码")
    public  void  drawCaptcha(@PathVariable("captchaId") String captchaId, HttpServletResponse response) throws IOException {
        //得到验证码 生成指定验证码
        String code= (String) redisTemplate.opsForValue().get(captchaId);
        if (StringUtils.isEmpty(code)){
            log.error("code为空");
        }
        CaptchaCodeUtil vCode = new CaptchaCodeUtil(116,36,4,10,code);
        vCode.write(response.getOutputStream());
    }




}
