package com.monkeyzi.oauth.sort;

import java.util.Arrays;

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
        int[] arr={33,2,3,45,55,1};
        sort2(arr);
        Arrays.stream(arr).forEach(a-> System.out.println(a));
    }
}
