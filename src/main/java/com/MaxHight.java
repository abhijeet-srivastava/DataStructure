package com;

public class MaxHight {


    private int sum(int h) {
        return h *(h+1)/2;
    }

    private int maxHight(int n) {
        if(n < 2) {
            return n;
        }
        int left = 1;
        int right = n;
        while(left < right) {
            int mid = left + (right-left)/2;
            int count = sum(mid);
            if(count < mid) {
                left = mid;
            } else if(count > mid) {
                right = mid;
            } else {
                return mid;
            }
        }
        return left;
    }
}
