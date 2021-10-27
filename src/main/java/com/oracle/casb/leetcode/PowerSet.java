package com.oracle.casb.leetcode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Created By : abhijsri
 * Date  : 2019-01-08
 **/
public class PowerSet {
    public static void main(String[] args) {
        PowerSet ps = new PowerSet();
        ps.testSet();
        //ps.testArrayList();
        //ps.testNotifications();
        ps.testPrevPerm();
    }

    private void testPrevPerm() {
        List<String> prevPerms = previousPermutations("DCBA");

        System.out.printf("%s -> %s\n", "DCBA" ,prevPerms.stream().map(String::valueOf).collect(Collectors.joining(" -> ")));
        List<String> nextPerms = nextPermutations("ABCD");
        System.out.printf("%s -> %s\n", "ABCD" ,nextPerms.stream().map(String::valueOf).collect(Collectors.joining(" -> ")));
    }

    private void testNotifications() {
        int[] arr = {1,2,3, 4, 4};
        int res = activityNotifications(arr, 4);
        System.out.println(res);
    }

    private void testArrayList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5, 5);
        String str = list.stream().map(String::valueOf).collect(Collectors.joining(","));
        System.out.println(str);
    }

    private void testSet() {
        permutation("abhijeet");
        Set<Set<Integer>> res = powerSet(ImmutableList.of(1,2,3));
        for (Set<Integer> set : res) {
            System.out.println(set.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
    }

    private Set<Set<Integer>> powerSet(List<Integer> set) {

        Set<Set<Integer>> res = new HashSet<>();
        int n = set.size();
        for (int b = 0; b < (1<<n); b++) {
            Set<Integer> subset = new HashSet<>();
            for (int i = 0; i < n; i++) {
                if ((b & (1<<i)) != 0) {
                    subset.add(set.get(i));
                };
            }
            res.add(subset);
        }
        return res;
    }
    public static void permutation(String str) {
        permutation("", str);
    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0)
            System.out.println(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
    }

    public int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < heights.length; i++) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i]) {
                maxArea = Math.max(maxArea, heights[stack.pop()] * (i - stack.peek() - 1));
            }
            stack.push(i);
        }
        while (stack.peek() != -1) {
            maxArea = Math.max(maxArea, heights[stack.pop()] * (heights.length - stack.peek() - 1));
        }
        return maxArea;
    }

    private int activityNotifications(int[] expenditure, int d) {
        int[] sorted = new int[expenditure.length];
        for (int i = 0; i < d; i++) {
            insertionSort(sorted, i, expenditure[i]);
        }

        int count = 0;
        for (int i = d; i < expenditure.length; i++) {
            long median = 0;
            if(i%2 == 0) {
                median = Long.valueOf(sorted[(i-1)/2]) + Long.valueOf(sorted[i/2]);
            } else {
                median = 2 * sorted[i/2];
            }
            if (expenditure[i] >= median) {
                count += 1;
            }
            insertionSort(sorted, i, expenditure[i]);
        }
        return count;
    }

    private  void insertionSort(int[] sorted, int index, int value) {
        int j = 0;
        if (index > 0 && value >= sorted[index-1]) {
            sorted[index] = value;
            return;
        }
        while (j < index && sorted[j] < value) {
            j += 1;
        }
        int k = index;
        for(; k > j; k--) {
            sorted[k] = sorted[k-1];
        }
        sorted[k] = value;
    }
    //Given weights and values of n items, put these items
    // in a knapsack of capacity W to get the maximum total value in the knapsack.
    private int knapsack(int[] weights, int[] values, int maxweight) {
        int itemCount = values.length;
        //knapsack[i][j]- Taken item from 1...i; and maxWeight j; ,aximum value
        int[][] knapsack = new int[itemCount][maxweight+1];
        //With 0 items, maximum value could be 0; whatever the size of Knapsack
        for (int weight = 0; weight <= maxweight; weight++) {
            knapsack[0][weight] = 0;
        }
        for (int item = 0; item < itemCount; item++) {
            for (int takenWeight = 0; takenWeight <= maxweight; takenWeight++) {
                if(weights[item] > takenWeight) {
                    knapsack[item][takenWeight] = knapsack[item-1][takenWeight];
                } else {
                    knapsack[item][takenWeight] = Math.max(knapsack[item-1][takenWeight],
                            values[item] + knapsack[item-1][takenWeight - weights[item]]);
                }
            }
        }
        return knapsack[itemCount][maxweight];
    }

    private int minEditDistance(String source, String target) {
        char[] srcArr = source.toCharArray();
        char[] targetArr =  target.toCharArray();
        int[][] distance = new int[source.length()][target.length()];
        for(int srcIndex = 0; srcIndex < source.length(); srcIndex++) {
            for (int targetIndex = 0; targetIndex < target.length(); targetIndex++) {
                if(srcIndex == 0 || targetIndex == 0){
                    distance[srcIndex][targetIndex] = 0;
                } else {
                    int deletion = distance[srcIndex-1][targetIndex] + 1;
                    int insertion = distance[srcIndex][targetIndex-1] + 1;
                    int substitution = distance[srcIndex-1][targetIndex-1];
                    if(srcArr[srcIndex] != targetArr[targetIndex]) {
                        substitution += 2;
                    }
                    distance[srcIndex][targetIndex] = Math.min(Math.min(deletion, insertion), substitution);
                }

            }
        }
        return distance[source.length()][target.length()];
    }
    public int longestPalindromeSubseq(String s) {
        return helper(s, 0, s.length() - 1, new Integer[s.length()][s.length()]);
    }

    private int helper(String s, int i, int j, Integer[][] memo) {
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        if (i > j)      return 0;
        if (i == j)     return 1;

        if (s.charAt(i) == s.charAt(j)) {
            memo[i][j] = helper(s, i + 1, j - 1, memo) + 2;
        } else {
            memo[i][j] = Math.max(helper(s, i + 1, j, memo), helper(s, i, j - 1, memo));
        }
        return memo[i][j];
    }

    private List<String> nextPermutations(String str) {
        List<String> res = new LinkedList<>();
        Character[] array = getCharactersArray(str);
        findNext(array, res);
        return res;
    }

    private Character[] getCharactersArray(String str) {
        Character[] array = new Character[str.length()];
        int i = 0;
        for (char ch : str.toCharArray()) {
            array[i++] = Character.valueOf(ch);
        }
        return array;
    }

    private void findNext(Character[] array, List<String> res) {
        int i = array.length - 1;
        while (i > 0 && array[i-1] > array[i]) {
            i -= 1;
        }
        if (i == 0) {
            return;
        }
        int min = i;
        int j = i+1;
        while (j < array.length && array[j] > array[i-1] ) {
            if(array[j] < array[min]) {
                min = j;
            }
            j += 1;
        }
        swap(array, i-1, min);
        Arrays.sort(array, i, array.length);
        res.add(String.valueOf(toString(array)));
        findNext(array, res);
    }

    private List<String> previousPermutations(String str) {
        List<String> res = new LinkedList<>();
        Character[] array = getCharactersArray(str);
        findPrevious(array, res);
        return res;
    }

    private void findPrevious(Character[] array, List<String> res) {
        int i = array.length - 1;
        while (i > 0 && array[i-1] < array[i]) {
            i -= 1;
        }
        if (i == 0) {
            return;
        }
        int max = i;
        int j = i+1;
        while (j < array.length && array[j] < array[i-1] ) {
            if(array[j] > array[max]) {
                max = j;
            }
            j += 1;
        }
        swap(array, i-1, max);
        Arrays.sort(array, i, array.length, Comparator.reverseOrder());
        res.add(String.valueOf(toString(array)));
        findPrevious(array, res);
    }

    private String toString(Character[] array) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(array).forEach(
                ch -> sb.append(ch)
        );
        return sb.toString();
    }

    private void swap(Character[] array, int i, int max) {
        char tmp = array[i];
        array[i] = array[max];
        array[max] = tmp;
    }
}
