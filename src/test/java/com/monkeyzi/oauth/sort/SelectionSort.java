package com.monkeyzi.oauth.sort;

import java.util.Arrays;

public class SelectionSort {

    public static  void  sort(int[] arr){
        int n=arr.length;
        for (int i=0;i<n;i++){
            int minIndex=i;
            for (int j=i+1;j<n;j++){
                if (arr[j]<arr[minIndex]){
                    minIndex=j;
                }
            }
            swap(arr,i,minIndex);
        }
    }

    private static  void swap(int[] arr,int i,int minIndex){
        int temp=arr[i];
        arr[i]=arr[minIndex];
        arr[minIndex]=temp;
    }
    public static void main(String[] args) {
        int[] arr={33,2,3,45,55,1};
        sort(arr);
        Arrays.stream(arr).forEach(a-> System.out.println(a));
    }
}
