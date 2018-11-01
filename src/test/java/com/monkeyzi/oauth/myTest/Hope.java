package com.monkeyzi.oauth.myTest;

public class Hope {

    public static void main(String[] args) {
        //Hope h=new Hope();
        String aa="java";
        String textString=new String("java");
        StringBuffer textBuffer=new StringBuffer("java");
        stringReplace(textString);
        stringReplace(aa);
        System.out.println(aa);
        System.out.println(textString);
        bufferReplace(textBuffer);
        System.out.println(textString+textBuffer);

    }
    public static void stringReplace(String text){
        text=text+"c";
    }
    public static void bufferReplace(StringBuffer text){
        text=text.append("c");
    }

    protected Hope(){
        for (int i=0;i<10;i++){
            System.out.println(i);
        }
    }
}
