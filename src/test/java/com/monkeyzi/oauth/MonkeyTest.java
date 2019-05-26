package com.monkeyzi.oauth;

import com.monkeyzi.oauth.entity.domain.Monkey;
import com.monkeyzi.oauth.service.MonkeyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;
import javax.sound.midi.Patch;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 高yg
 * @date: 2018/12/11 20:55
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class MonkeyTest extends OauthApplicationTests {

    @Autowired
    private MonkeyService monkeyService;

    @Resource
    private TaskExecutor taskExecutor;
    private static final int PATCH=3;

    @Test
    public void test1(){

        List<Monkey>  list=new ArrayList<>();
        for (int i=0;i<10;i++)
        {
            Monkey m=new Monkey();
            m.setId(i+"");
            m.setName("name-"+i);
            list.add(m);
        }

        int times= (int) Math.ceil(list.size()/Double.valueOf(PATCH));
        System.out.println("次数："+times);
        taskExecutor.execute(()->{

            System.out.println("插入数据："+list.size());
            String threadName=Thread.currentThread().getName();
            for (Monkey mm:list){
                System.out.println(mm);
                monkeyService.insertToDb(mm);
                System.out.println(threadName+"====mm="+mm.getName());

            }
        });
        /*for (int i=0;i<times;i++){
            final int j=i;
            List<Monkey> monkeyList=list.subList(j*PATCH,
                    Math.min((j+1)*PATCH,
                            list.size()));
            taskExecutor.execute(()->{

                System.out.println("插入数据："+monkeyList.size());
                String threadName=Thread.currentThread().getName();
                for (Monkey mm:monkeyList){
                    System.out.println(threadName);
                    monkeyService.insertToDb(mm);
                    System.out.println(threadName+"====mm="+mm.getName());

                }
            });
        }*/
    }

}
