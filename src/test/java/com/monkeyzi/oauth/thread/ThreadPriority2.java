package com.monkeyzi.oauth.thread;

/**
 * @author: 高yg
 * @date: 2018/12/16 12:36
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class ThreadPriority2 {
    public static void main(String[] args) {
        ThreadGroup threadGroup=new ThreadGroup("test");
        threadGroup.setMaxPriority(7);
        Thread thread=new Thread(threadGroup,"thread-test");
        thread.setPriority(10);
        System.out.println(thread.getPriority());
    }
}
