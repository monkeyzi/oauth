package com.monkeyzi.oauth.myTest;

public class Hope1 {
    public static void main(String[] args) {
        String a="My field1";
        String b="My field1";
        String c=new String("My field1");
        String d=new String("My field1");
        System.out.println(a==b);
        System.out.println(a==c);
        System.out.println(c==d);
        System.out.println(a.equals(b));
        System.out.println(a.equals(c));
        System.out.println(c.equals(d));

    }



}
