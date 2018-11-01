package com.monkeyzi.oauth.myTest;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class D {

    public static void main(String[] args) throws ClassNotFoundException {
        D d=new D();
        System.out.println(d.getClass());
        System.out.println(D.class);
        System.out.println(Class.forName("com.monkeyzi.oauth.myTest.D"));
        //System.out.println(Class.forName("D")); 错
        try{
            PrintWriter out  =
                    new PrintWriter(new FileOutputStream("d:/abc.txt"));
            String name="chen";
            out.print(name);
        }catch(Exception e){
            System.out.println("文件没有发现！");
        }

    }
}
