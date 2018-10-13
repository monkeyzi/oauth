package com.monkeyzi.oauth;

import com.monkeyzi.oauth.entity.Permission;
import com.monkeyzi.oauth.service.PermissionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/10/13 18:26
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class PermissionServiceTest extends OauthApplicationTests {

    @Autowired
    private PermissionService permissionService;

    @Test
    public void  test1(){
        List<Permission> list=permissionService.selectAll();
        System.out.println(list);
    }
}
