package com.monkeyzi.oauth.myTest;

import java.util.Arrays;

/**
 * @author: 高yg
 * @date: 2018/11/3 19:50
 * @qq:854152531@qq.com
 * @blog http://www.monkeyzi.xin
 * @description:
 */
public class InsertSort {


    public static void insert(int[] arr){
        int n=arr.length;
        for (int i=1;i<n;i++){
            int temp=arr[i];
            int j;
            for (j=i-1;j>=0&&arr[j]>temp;j--){
                //if (arr[j]>temp){

                    arr[j+1]=arr[j];

               // }
            }
            arr[j+1]=temp;




        }
    }


    public static   void  bubbleSort(int[] arr){
        int n=arr.length;
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                if (arr[i]>arr[j]){
                   int temp=arr[j];
                   arr[j]=arr[i];
                   arr[i]=temp;
                }
            }
        }

    }

    public static void smallToLarge()
    {
        int test[]={10,5,2,15,9,20,3};

        for(int i=0;i<test.length-1;i++)
        {
            if(test[i]>test[i+1])
            {
                int temp=test[i];
                test[i]=test[i+1];
                test[i+1]=temp;
            }
        }

        for(int i=0;i<test.length;i++)
        {
            System.out.println(test[i]);
        }
    }

    public static void largeToSmall()
    {
        int test[]={10,5,2,15,9,20,3};

        for(int i=0;i<test.length-1;i++)
        {
            for(int j=i+1;j<test.length-1;j++)
            {
                if(test[i]<test[j])
                {
                    int temp=test[i];
                    test[i]=test[j];
                    test[j]=temp;
                }
            }
        }

        for(int i=0;i<test.length;i++)
        {
            System.out.println(test[i]);
        }
    }


    /*public static void main(String[] args) {
        *//*int arr[] ={33,66,6,7,4,21};
        long start=System.currentTimeMillis();
        bubbleSort(arr);
        long end=System.currentTimeMillis();
        System.out.println(end-start);
        Arrays.stream(arr).forEach(a-> System.out.println(a));*//*
        largeToSmall();
    }*/

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int score[] = { 66, 29, 4, 55, 5, 9 };
        for (int i = 0; i < score.length - 1; i++) { // 最多做n-1趟排序
            for (int j = 0; j < score.length - i - 1; j++) { // 对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
                if (score[j] > score[j + 1]) { // 把大的值交换到后面
                    int temp = score[j];
                    score[j] = score[j + 1];
                    score[j + 1] = temp;
                }
            }
            System.out.print("第" + (i + 1) + "趟排序结果：");
            for (int a = 0; a < score.length; a++) {
                System.out.print("\t"+score[a]+"\t");
            }
            System.out.println("");
        }
        System.out.print("最终排序结果：");
        for (int a = 0; a < score.length; a++) {
            System.out.print("\t"+score[a]+"\t");
        }
    }

}
