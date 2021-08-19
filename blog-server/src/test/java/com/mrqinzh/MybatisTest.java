package com.mrqinzh;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MybatisTest {

    @Test
    public void test() {
        int[] arr = {0,1,5,9,6,7,2,5,7,6,2,7,6,9,4,10};
        quickSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    private void quickSort(int[] arr, int start, int end) {
        if (start > end) return;
        int pivot = partition(arr, start, end);
        quickSort(arr, start, pivot-1);
        quickSort(arr, pivot+1, end);
    }

    public int partition(int[] arr, int start, int end) {
        int pivot = arr[start];
        int left = start;
        int right = end;
        while (left < right) {
            while (left < right && pivot <= arr[right]) {
                right--;
            }
            while (left < right && pivot > arr[left]) {
                left++;
            }
            int tmp = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
        }

        arr[start] = arr[left];
        arr[left] = pivot;
        return left;
    }

}
