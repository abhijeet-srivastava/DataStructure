package com.oracle.casb;

import java.util.Arrays;

/**
 * Created By : abhijsri
 * Date  : 15/07/18
 **/
public class Counter {

    private static int count = 0;

    public Counter() {
        ++count;
    }

    public static void main(String[] args) {
        Counter c1 = new Counter();
        /*Counter c2 = new Counter();
        Counter c3 = new Counter();
        Counter c4 = new Counter();

        System.out.println("Total " + count);*/
//        c1.getRes();
//        String str = c1.shortestAfter("hackerearth", 3);
//        System.out.println("Converted String " + str);
        String original = "somerandomstring";
        System.out.printf("Reverse of %s is %s\n", original, c1.reverse(original));
    }

    private String reverse(String str) {
        StringBuilder sb = new StringBuilder();
        return reverseRecursive(str, 0);
    }

    private String reverseRecursive(String str, int index) {
        if(index == str.length()-1) {

            return String.valueOf(str.charAt(index));
        } else {
            return reverseRecursive(str, index+1) +  String.valueOf(str.charAt(index));
        }
    }

    private void getRes() {
        int[] array = new int[]{1,2,3,4,5};
        int[] input = array;
        int[] res = getMaxSubSetLessOrEqualAvg(array, input);
        Arrays.stream(res).forEach(System.out::println);
    }
    /**
     * First you can select 'a' from "hackerearth". Now the string X becomes "a" and string S becomes "hckerearth".
     *
     * Now after applying the operation again, the string X becomes "ac" and the string S becomes "hkerearth".
     *
     * Similarly after applying the operation n times, the string X becomes "aceheakrhrt".
     */

    /**
     * Sample Input
     * 5
     * 1 2 3 4 5
     * 5
     * 1
     * 2
     * 3
     * 4
     * 5
     * Sample Output
     * 0
     * 2
     * 4
     * 5
     * 5
     * Explanation
     * In first query, there is no possible subset such that its average is less than 1.
     *
     * In second query, you can select the subset {1,2}.
     *
     * In third query, you can select the subset {1,2,3,4}.
     *
     * In fourth and fifth query, you can seelct the complete array {1,2,3,4,5}.
     */

    private String shortestAfter(String str, int K) {
        //hackerearth
        char[] input = str.toCharArray();
        char[] res = new char[input.length];
        char[] sorted = new char[K];
        for (int i = 0; i < K; i++) {
            char key = input[i];
            sorted[i] = key;
            int j = i-1;
            for (; j >= 0 && key < sorted[j]; j-- ) {
                sorted[j+1] = sorted[j];
            }
            sorted[j+1] = key;
        }
        res[0] = sorted[0];
        for ( int i = K; i <= input.length - K + 1; i++) {
            insertSorted(sorted, input[i]);
            res[i - K + 1] = sorted[0];
        }
        insertSorted(sorted, input[input.length - 1]);
        for(int i = 0; i < K; i++) {
            res[i+ input.length - K] = sorted[i];
        }
        return new String(res);
    }

    private void insertSorted(char[] sorted, char ch) {
        //char key = sorted[sorted.length - 1];
        int j = 1;
        while (j < sorted.length
                &&  sorted[j] < ch) {
            sorted[j-1] = sorted[j];
            ++j;
        }
        sorted[j-1] = ch;
    }

    public int[] getMaxSubSetLessOrEqualAvg(int[] array, int[] input) {
        int[] res = new int[input.length];
        int[] cumlativeSum = new int[array.length];
        Arrays.sort(array);
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            cumlativeSum[i] = sum;
        }
        for (int i = 0; i < input.length; i++) {
            res[i] = getMaxLength(cumlativeSum, input[i]);
        }
        return res;
    }

    private int getMaxLength(int[] cumlativeSum, int avg) {
        int i = 0;
        while (i < cumlativeSum.length
                && ((avg * (i + 1) > cumlativeSum[i]))) {
            ++i;
        }

        return i;

    }
}
