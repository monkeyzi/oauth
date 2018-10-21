package com.monkeyzi.oauth;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.monkeyzi.oauth.entity.domain.User;
import com.monkeyzi.oauth.entity.domain.UserDepartment;
import com.monkeyzi.oauth.entity.dto.user.UserQueryDto;
import com.monkeyzi.oauth.mapper.UserDepartmentMapper;
import com.monkeyzi.oauth.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class UserServiceTest extends OauthApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDepartmentMapper userDepartmentMapper;

    @Test
    public void test1(){
        UserQueryDto dto=new UserQueryDto();
        //dto.setUsername("gaoyanguo");
        dto.setQueryStartTime(DateUtil.parse("2018-10-11"));
        dto.setQueryEndTime(DateUtil.endOfDay(DateUtil.parse("2018-10-18")));
        PageInfo pageInfo =userService.queryUserListWithPage(dto);
        List<User> userList=pageInfo.getList();

        System.out.println("获取到的用户信息为:"+userList.size());
        userList.forEach(a->{
            System.out.println(a.getUsername()+":"+a.getLastLoginTime());
        });
    }


    @Test
    public void test2(){
        UserDepartment userDept=new UserDepartment();
        userDept.setUserId("003");
        userDepartmentMapper.delete(userDept);
        System.out.println("删除成功");
    }

    public static void main(String[] args) {
        Date date=DateUtil.endOfDay(DateUtil.parse("2018-10-18"));
        System.out.println(date);
    }
}
