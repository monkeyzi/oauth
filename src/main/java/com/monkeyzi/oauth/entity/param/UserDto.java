package com.monkeyzi.oauth.entity.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: 高yg
 * @date: 2018/10/15 22:02
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
public class UserDto {

    @NotBlank(message = "用户名不能为空")
    private String userName;
    private String sex;
}
