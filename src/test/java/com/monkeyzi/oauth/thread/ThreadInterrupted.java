package com.monkeyzi.oauth.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author: 高yg
 * @date: 2018/12/16 13:10
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class ThreadInterrupted {
    public static void main(String[] args) {

        System.out.println("main Thread is interrupted?"+Thread.interrupted());
        Thread.currentThread().interrupt();
        System.out.println("current thread is interrupted?"+Thread.currentThread().isInterrupted());
        try {
            TimeUnit.MINUTES.sleep(1);
        }catch (InterruptedException e){
            System.out.println("纵断了");
        }
    }
}
