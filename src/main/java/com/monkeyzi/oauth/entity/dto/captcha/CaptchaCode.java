package com.monkeyzi.oauth.entity.dto.captcha;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 高yg
 * @date: 2018/10/17 22:31
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@ApiModel(value = "验证码")
public class CaptchaCode {

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "验证码Id")
    private String captchaId;

}
