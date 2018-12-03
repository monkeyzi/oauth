package com.monkeyzi.oauth.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InsertSort {


    public static  void sort(int[] arr){
        int n=arr.length;
        for (int i=0;i<n;i++){
            int j;
            for (j=i;j>0;j--){
                if (arr[j-1]>arr[j]){
                    int temp=arr[j];
                    arr[j]=arr[j-1];
                    arr[j-1]=temp;
                }else {
                    break;
                }
            }
        }
    }
    public static  void sort2(int[] arr){
        int n=arr.length;
        for (int i=0;i<n;i++){
            int j;
            for (j=i;j>0&&arr[j-1]>arr[j];j--){
               int temp=arr[j];
               arr[j]=arr[j-1];
               arr[j-1]=temp;
            }
        }
    }

    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list.add("5");
        list.add("4");
        list.add("78");
        list.add("8888");
        int from=2,to=3;
        list.add(from,"8888");
        System.out.println(list);
        list.remove(to+1);
        System.out.println(list);
    }
}
