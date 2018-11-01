package com.monkeyzi.oauth.myTest;

public class C extends Thread {

    public static void main(String argv[]){
        C b = new C();
        b.run();
    }


    public void start(){
        for (int i = 0; i <10; i++){
            System.out.println("Value of i = " + i);
        }
    }

}
