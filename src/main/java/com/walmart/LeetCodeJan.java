package com.walmart;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LeetCodeJan {
    public static void main(String[] args) {
        LeetCodeJan lcj = new LeetCodeJan();
        lcj.test11Jan();
    }

    private void test11Jan() {
        int nums1[] = {1,2,3,0,0,0};
        int nums2[] = {2,5,6};
        merge(nums1, 3, nums2, 3);
        System.out.printf("%s\n", Arrays.stream(nums1).mapToObj(Integer::toString).collect(Collectors.joining(", ")));
    }
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = 0;
        int j = 0;
        while(i < m || j < n) {
            int arr1 = (i < m) ? nums1[i] : Integer.MAX_VALUE;
            int arr2 = (j < n) ? nums2[j] : Integer.MAX_VALUE;
            if(arr1 > arr2) {
                int index = i;
                while(index < m) {
                    System.out.printf("index: %d, \n", index);
                    int tmp = nums1[index + 1];
                    nums1[index + 1] = nums1[index];
                    index += 1;
                }
                nums1[i] = nums2[j];
                j += 1;
            }
            i += 1;
        }
    }
}
