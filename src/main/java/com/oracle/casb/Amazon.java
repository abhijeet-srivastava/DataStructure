package com.oracle.casb;

import com.oracle.casb.common.ArrayUtils;
import com.oracle.casb.common.TreeNode;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By : abhijsri
 * Date  : 18/06/18
 **/
public class Amazon {
    public static void main(String[] args) {
        Amazon az = new Amazon();
        az.printSupportedFonts();
        //az.solveLeastGreatest();
        //az.solveLeastMax();
        //System.out.println(az.minSwapsCouples(new int[]{1,4,0,5,8,7,6,3,2,9}));
        //System.out.println(az.minSwapsCouples(new int[]{6,2,1,7,4,5,3,8,0,9}));
        int score =az.scoreOfParentheses("()()");
        System.out.println("Score " + score);
    }

    public boolean lemonadeChange(int[] bills) {
        boolean res = true;
        int[] changeInHand = new int[]{0, 0, 0};
        for (int i = 0; i < bills.length; i++) {
            int remaining = bills[i] - 5;
            switch (bills[i]) {
                case 5:
                    changeInHand[0] += 1;
                    res = getRemaining(remaining, changeInHand);
                    break;
                case 10:
                    changeInHand[1] += 1;
                    res = getRemaining(remaining, changeInHand);
                    break;
                case 20:
                    changeInHand[2] += 1;
                    res = getRemaining(remaining, changeInHand);
                    break;
                default:
                    res = false;
                    break;
            }
            if (!res) {
                break;
            }
        }

        return res;
    }

    private boolean getRemaining(int remaining, int[] changeInHand) {
        boolean isPossible = false;
        if (remaining == 0) {
            return true;
        }else if (remaining == 5
                && changeInHand[0] > 0) {
            changeInHand[0] -= 1;
            isPossible= getRemaining(remaining - 5, changeInHand);
        } else if (remaining == 10) {
            if (changeInHand[1] > 0) {
                changeInHand[1] -= 1;
                isPossible= getRemaining(remaining - 10, changeInHand);
            } else if (changeInHand[0] > 0) {
                changeInHand[0] -= 1;
                isPossible= getRemaining(remaining - 5, changeInHand);
            }
        } else if (remaining == 15) {
            if (changeInHand[1] > 0) {
                changeInHand[1] -= 1;
                isPossible= getRemaining(remaining - 10, changeInHand);
            } else if (changeInHand[0] > 0) {
                changeInHand[0] -= 1;
                isPossible= getRemaining(remaining - 5, changeInHand);
            }
        }
        return isPossible;
    }

    private void printSupportedFonts() {
        String fonts[] =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for ( int i = 0; i < fonts.length; i++ )
        {
            System.out.println(fonts[i]);
        }
    }

    private void solveLeastMax() {
        int[] input = new int[]{2,5,1,11,7,12,4,3};
        int[] result = new int[input.length];
        TreeNode<Integer>[] rootRef = new TreeNode[1];
        TreeNode<Integer>[] successor = new TreeNode[]{new TreeNode<Integer>(-1)};
        for (int i = input.length - 1; i >= 0; i--) {
            leastGreater(rootRef, successor, input[i]);
            result[i] = successor[0].getValue();
            successor = new TreeNode[]{new TreeNode<Integer>(-1)};
        }
        System.out.println(Arrays.toString(result));
    }

    private void solveLeastGreatest() {
        int[] input = new int[]{1,4,0,5,8,7,6,3,2,9};
        int[] result = solveLeastGreatest(input);
        System.out.println(Arrays.toString(result));
    }

    private int[] solveLeastGreatest(int[] input) {
        int[] result = new int[input.length];
        TreeNode<Integer> root = new TreeNode<>(input[input.length - 1]);
        TreeNode<Integer>[] successor = new TreeNode[1];
        result[input.length - 1] = -1;
        for (int i = input.length - 2; i >= 0; i--) {
            insertInTree(root, input[i], successor);
            if (successor[0] == null) {
                result[i] = -1;
            } else {
                result[i] = successor[0].getValue();
            }
            successor = new TreeNode[1];
        }
        return result;
    }
    private void leastGreater(TreeNode<Integer>[] rootRef, TreeNode<Integer>[] successor, int value) {
        if (rootRef[0] == null) {
            rootRef[0] = new TreeNode<>(value);
            successor[0] = new TreeNode<>(-1);
            return;
        }
        if (value < rootRef[0].getValue()) {
            if (rootRef[0].getLeft() == null) {
                rootRef[0].setLeft(new TreeNode<>(value));
            } else {
                leastGreater(new TreeNode[]{rootRef[0].getLeft()}, successor, value);
            }
        } else {
            successor[0] = rootRef[0];
            if (rootRef[0].getRight() == null) {
                rootRef[0].setRight(new TreeNode<>(value));
            } else {
                leastGreater(new TreeNode[]{rootRef[0].getRight()}, successor, value);
            }
        }

    }

    public int getDrops(int eggs, int floors){
        //base case 1:
        //if floors = 0 then no drops are required OR floors = 1 then 1 drop is required
        if(floors==0 || floors==1)
            return floors;

        //base case 2:
        //if only one egg is there then drops = floors
        if(eggs==1)
            return floors;

        int minimumDrops=Integer.MAX_VALUE, tempResult;
        //check dropping from all the floors 1 to floors and pick the minimum among those.
        //for every drop there are 2 scenarios - a) either egg will break b) egg will not break
        for (int i = 1; i <=floors ; i++) {
            //for the worst case pick the maximum among a) and b)
            tempResult = Math.max(getDrops(eggs-1,i-1), getDrops(eggs, floors-i));
            minimumDrops = Math.min(tempResult,minimumDrops);
        }
        return minimumDrops + 1;
    }

    private void insertInTree(TreeNode<Integer> root, int i, TreeNode<Integer>[] successor) {
        if (i < root.getValue()) {
            successor[0] = root;
            if (root.getLeft() == null) {
                root.setLeft(new TreeNode<>(i));
            } else {
                insertInTree(root.getLeft(), i, successor);
            }
        } else {
            if (root.getRight() == null) {
                root.setRight(new TreeNode<>(i));
            } else {
                insertInTree(root.getRight(), i, successor);
            }
        }
    }

    private int minSwapsCouples(int[] row) {
        int swaps = 0;
        int[][] couples = new int[row.length/2][2];
        for (int i = 0; i < row.length; i++) {
            if (row[i]%2 == 0) {
                couples[row[i]/2][0] = i/2;
            } else {
                couples[row[i]/2][1] = i/2;
            }
        }
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < couples.length - 1; i++) {
            if (couples[0] == couples[1] || visited.contains(i)) {
                visited.add(i);
                continue;
            }
            Set<Integer> elementSet = new HashSet<>();
            List<Integer> list = new ArrayList<>();
            elementSet.add(couples[i][0]);
            elementSet.add(couples[i][1]);
            list.add(i);
            visited.add(i);
            for (int j = i+1; j < couples.length; j++) {
                if (!visited.contains(j)
                        && (elementSet.contains(couples[j][0])
                        || elementSet.contains(couples[j][1]))) {
                    if (elementSet.contains(couples[j][0])) {
                        elementSet.remove(couples[j][0]);
                        elementSet.add(couples[j][1]);
                        visited.add(j);
                    }
                    if (elementSet.contains(couples[j][1])) {
                        elementSet.remove(couples[j][1]);
                        elementSet.add(couples[j][0]);
                        visited.add(j);
                    }
                    if (elementSet.isEmpty()) {
                        break;
                    }
                    list.add(j);
                }
            }
            swaps += (list.size() - 1);
        }
        /*for (int i = 0; i < row.length/2; i++) {
            if (couples[i][0] != couples[i][1]) {
                swaps += 1;
            }
        }
        if (swaps > 0) {
            swaps -= 1;
        }*/
        return swaps;
    }
    public int scoreOfParentheses(String S) {
        if (isSinglePanatheses(S, 0, S.length() - 1)) {
            return 1;
        }
        return getScore(S, 0, S.length() - 1);
    }

    private boolean isSinglePanatheses(String S, int start, int end) {
        return (end - start) == 1
                && S.charAt(start) == '(' && S.charAt(end) == ')';
    }

    private int getScore(String str, int start, int end) {
        int score = 0;
        if (isSinglePanatheses(str, start, end)) {
            return 1;
        }
        int count = 0;
        int startN = start;
        for (int i = start; i <= end; i++) {
            if (str.charAt(i) == '(') {
                ++count;
            } else if (str.charAt(i) == ')') {
                --count;
            }
            if (count <= 0) {
                int newScore = 0;
                if (isSinglePanatheses(str, startN, i)) {
                    newScore = 1;
                } else {
                    newScore = 2 * getScore(str, startN + 1, i - 1);
                }
                score += newScore;
                count = 0;
                startN = i + 1;
            }

        }
        return score;
    }
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums == null || nums.length < 3) {
            return result;
        }
        Arrays.sort(nums);
        Set<String> set = new HashSet<>();
        Map<Integer, List<int[]>> CACHE = new HashMap<>();
        for(int i = 0; i < nums.length-2; i++) {
            final Integer val = Integer.valueOf(nums[i]);
            final int startInd = i+1;
            List<int[]> list = CACHE.computeIfAbsent(val, (e) -> findTwoSum(nums, startInd, 0-val));
            if(!list.isEmpty()) {
                for(int[] arr : list) {
                    Integer[] array = {nums[i], arr[0], arr[1]};
                    String key
                            = Arrays.stream(array).map(String::valueOf).collect(Collectors.joining(","));
                    if(!set.contains(key)) {
                        result.add(Arrays.asList(array));
                        set.add(key);
                    }
                }
            }
        }
        return result;
    }
    private List<int[]> findTwoSum(int[] nums, int startIndex, int sum) {
        List<int[]> list = new ArrayList<>();
        int hi = nums.length-1;
        int lo = startIndex;
        while(lo < hi) {
            int val = nums[lo] + nums[hi];
            if(val == sum) {
                list.add(new int[]{nums[lo++], nums[hi--]});
            } else if(val < sum) {
                lo += 1;
            } else {
                hi -= 1;
            }
        }
        return list;
    }
}