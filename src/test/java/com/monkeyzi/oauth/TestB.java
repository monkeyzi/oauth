package com.monkeyzi.oauth;

public class TestB extends TestA {

    public int getNumber(int a,char c){
        return a+2;
    }
    public int count(){
        return 1%9;
    }


    public static void main(String[] args) {
        TestB testB=new TestB();
        System.out.println(testB.getNumBer(0));
        int[] i=new int[5];
        System.out.println(i.length);
        String  a=new String("123");
        System.out.println(i[5]); //报错
    }
}
