package com.monkeyzi.oauth.entity.vo.roleuser;

import com.monkeyzi.oauth.entity.dto.roleuser.BindRoleDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author: 高yg
 * @date: 2018/10/21 20:21
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
@Data
@ApiModel(value = "角色列表")
public class BindRoleVo implements Serializable {

    private static final long serialVersionUID = -3385971785265488527L;

    private Set<BindRoleDto> bindRoleSet;
}