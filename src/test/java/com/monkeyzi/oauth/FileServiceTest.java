package com.monkeyzi.oauth;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.FileFolder;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.file.FileQueryDto;
import com.monkeyzi.oauth.service.FileService;
import com.monkeyzi.oauth.utils.ThreadLocalMapUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: 高yg
 * @date: 2018/11/10 13:42
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class FileServiceTest extends OauthApplicationTests {

    @Autowired
    private FileService fileService;


    @Test
    public void test1(){
        LoginAuthDto loginAuthDto= new LoginAuthDto();
        loginAuthDto.setUserName("dd");
        FileQueryDto deo=new FileQueryDto();
        deo.setFolderId("2");
        PageInfo pageInfo = fileService.queryFileByFolder(deo, loginAuthDto);
        System.out.println(pageInfo);
    }
}
