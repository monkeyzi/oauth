package com.monkeyzi.oauth.myTest;

public class B extends A {
    public B(){
        System.out.println("b");
    }
    static int i;
    public int test(){
        i++;
        return i;
    }

    public static void main(String[] args) {
        //B b=new B();
        B test=new B();
        test.test();
        System.out.println(test.test());
    }

}
