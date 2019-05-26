package com.monkeyzi.oauth.thread;

import java.util.stream.IntStream;

/**
 * @author: 高yg
 * @date: 2018/12/16 12:24
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class ThreadYield {

    public static void main(String[] args) {
        IntStream.range(0,2).mapToObj(ThreadYield::create).forEach(Thread::start);
    }

    private static Thread create(int index){
        return new Thread(()->{
            if (index==0)
                Thread.yield();
            System.out.println(index);
        });
    }
}
