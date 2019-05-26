package com.monkeyzi.oauth.thread;

/**
 * @author: 高yg
 * @date: 2018/12/16 12:15
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class ThreadSleep {
    public static void main(String[] args) {

        new Thread(()->{
           long startTime=System.currentTimeMillis();
           sleep(2000L);
           long endTime=System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+"spend"+(endTime-startTime)+"ms");
        }).start();
        long startTime=System.currentTimeMillis();
        sleep(3000L);
        long endTime=System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName()+"spend:"+(endTime-startTime)+"ms");


    }

    private static void sleep(long ms){
        try {
            Thread.sleep(ms);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
