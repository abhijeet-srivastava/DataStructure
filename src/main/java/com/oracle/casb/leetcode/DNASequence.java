package com.oracle.casb.leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class DNASequence {
    //"2345" length = 3

    /**
     * Rh("234") = 234 //for "234"
     * Rh("345") = (234 - 2 * (10)^2) * 10 + 5 = 345
     * hash( txt[s+1 .. s+m] ) =  ( hash( txt[s .. s+m-1]) – txt[s]*(d^(m-1)) )*d + txt[s + m]
     * @param args
     */

    public static void main(String[] args) {
        DNASequence ds = new DNASequence();
        //ds.testPower();
        //ds.testReverseString();
        ds.testMostFrequent();
    }

    private void testMostFrequent() {
        int[] arr = {5,2,5,3,5,3,1,1,3};
        int[] ret = topKFrequent(arr, 2);
        String str= Arrays.stream(ret).boxed().map(String::valueOf).collect(Collectors.joining(","));
        System.out.printf("%s\n", str);
    }

    public int[] topKFrequent(int[] nums, int k) {
        if(k == nums.length) {
            return nums;
        }
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for(int num : nums) {
            frequencyMap.merge(num, 1, Integer::sum);
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(k+1, (a, b) -> frequencyMap.get(a) -  frequencyMap.get(b));
        for(int num : frequencyMap.keySet()) {
            queue.add(num);
            if(queue.size() > k) {
                queue.poll();
            }
        }
        int[] result = new int[k];
        for(int i = 0; i < result.length; i++) {
            result[i] = queue.remove();
        }
        return result;

        /*int[] result = new int[k];
        int[] minMax = new int[2];
        minMax[0] = Integer.MAX_VALUE;
        minMax[1] = Integer.MIN_VALUE;
        for(int i : nums) {
            minMax[0] = Math.min(i, minMax[0]);
            minMax[1] = Math.max(i, minMax[1]);
        }
        int len = minMax[1] - minMax[0] + 1;
        int[] arr = new int[len];
        for(int i : nums) {
            arr[i-minMax[0]] += 1;
        }
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> b[1]-a[1]);
        for(int i = 0; i < arr.length; i++) {
            queue.offer(new int[]{i+minMax[0], arr[i]});
        }
        for(int i = 0; i < k; i++) {
            result[i] = queue.remove()[0];
        }
        return result;*/
    }
    private void testReverseString() {
        String res = reverseWords("a good   example");
    }

    public String reverseWords(String s) {
        char[] arr = s.toCharArray();
        int start = 0;
        int end = arr.length-1;
        while(Character.isSpaceChar(arr[start])) {
            start += 1;
        }
        while(Character.isSpaceChar(arr[end])) {
            end -= 1;
        }
        reverse(arr, start, end);
        //System.out.printf("%s\n", String.valueOf(arr));
        StringBuilder sb = new StringBuilder();
        int j = start;
        for(int i = start; i <= end; i++) {
            char ch = arr[i];
            if(Character.isSpaceChar(ch)) {
                reverse(arr, j, i-1);
                sb.append(String.valueOf(Arrays.copyOfRange(arr, j, i)));
                sb.append(" ");
                while(i <= end && Character.isSpaceChar(arr[i])) {
                    i += 1;
                }
                j=i;
            }
        }
        if(j <= end) {
            reverse(arr, j, end);
            sb.append(String.valueOf(Arrays.copyOfRange(arr, j, end+1)));
        }
        return sb.toString();
    }

    public void reverse(char[] arr, int start, int end) {
        while(start < end) {
            char tmp = arr[start];
            arr[start] = arr[end];
            arr[end] = tmp;
            start += 1;
            end -= 1;
        }
    }
    private void testPower() {
        int x = power(4, 3);
        System.out.printf("%d\n", x);
    }

    private int power(int x, int n) {
        if(n == 0) {
            return 1;
        }
        int half = power(x, n >> 1);
        return half*half*(((n&1) == 1) ? x:1);
    }

    public List<String> findRepeatedDnaSequences(String s) {
        int n = s.length();
        if(s == null || n < 10) {
            return Collections.EMPTY_LIST;
        }
        Map<Character, Integer> charToValues = new HashMap<>();
        charToValues.put('A', 1);
        charToValues.put('C', 2);
        charToValues.put('G', 3);
        charToValues.put('T', 4);
        Map<Integer, Map<String, Integer>> hashCodeMap = new HashMap<>();
        int hashCode = 0;
        return null;
    }
}
