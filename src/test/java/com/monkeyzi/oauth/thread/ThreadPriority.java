package com.monkeyzi.oauth.thread;

/**
 * @author: 高yg
 * @date: 2018/12/16 12:31
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class ThreadPriority {

    public static void main(String[] args) {
        Thread t1=new Thread(()->{
            while (true){
                System.out.println("t1");
            }
        });
        t1.setPriority(3);
        Thread t2=new Thread(()->{
            while (true){
                System.out.println("t2");
            }
        });
        t2.setPriority(10);
        t1.start();
        t2.start();
    }
}
