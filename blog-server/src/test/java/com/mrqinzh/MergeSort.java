package com.mrqinzh;

import org.junit.jupiter.api.Test;

public class MergeSort {

    public void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(arr, start, mid);
            mergeSort(arr, mid+1, end);
            merge(arr, start, mid, end);
        }
    }

    private void merge(int[] arr, int start, int mid, int end) {
        int[] tmp = new int[arr.length];
        int p1 = start;
        int p2 = mid+1;
        int k = start;

        while (p1 <= mid && p2 <= end) {
            if (arr[p1] < arr[p2]) {
                tmp[k++] = arr[p1++];
            } else {
                tmp[k++] = arr[p2++];
            }
        }

        while(p1<=mid) tmp[k++]=arr[p1++];//如果第一个序列未检测完，直接将后面所有元素加到合并的序列中
        while(p2<=end) tmp[k++]=arr[p2++];//同上

        //复制回原素组
        for (int i = start; i <=end; i++)
            arr[i]=tmp[i];

    }

    @Test
    public void test(){
        int[] a = { 49, 38, 65, 97, 76, 13, 27, 50 };
        mergeSort(a, 0, a.length-1);
        System.out.println("排好序的数组：");
        for (int e : a)
            System.out.print(e+" ");
    }

}
