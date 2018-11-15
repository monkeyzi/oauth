package com.monkeyzi.oauth;

import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.common.GlobalConstant;
import com.monkeyzi.oauth.entity.domain.FileFolder;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.entity.dto.file.FileQueryDto;
import com.monkeyzi.oauth.entity.dto.tree.TreeDto;
import com.monkeyzi.oauth.service.FileFolderService;
import com.monkeyzi.oauth.service.FileService;
import com.monkeyzi.oauth.utils.ThreadLocalMapUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

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
    @Autowired
    private FileFolderService fileFolderService;


    @Test
    public void test1(){
        LoginAuthDto loginAuthDto= new LoginAuthDto();
        loginAuthDto.setUserName("dd");
        FileQueryDto deo=new FileQueryDto();
        deo.setFolderId("2");
        PageInfo pageInfo = fileService.queryFileByFolder(deo, loginAuthDto);
        System.out.println(pageInfo);
    }


    @Test
    public void test2(){
        LoginAuthDto loginAuthDto= new LoginAuthDto();
        loginAuthDto.setUserName("dd");
        loginAuthDto.setRoleList(Arrays.asList("0"));

        List<TreeDto> treeDtoList=fileFolderService.queryFolder(loginAuthDto);
        System.out.println(treeDtoList);
    }
}
