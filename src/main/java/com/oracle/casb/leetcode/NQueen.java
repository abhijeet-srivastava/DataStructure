package com.oracle.casb.leetcode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NQueen {

    public static void main(String[] args) {
        NQueen nq = new NQueen();
        int[][] array = {{10,20},{30,200}, {400,50},{30,20}};
        //nq.twoCitySchedCost(array);
        List<Integer> res = nq.solveNQueen(8);
        res.stream().forEach(System.out::println);
    }
    public List<Integer> solveNQueen(int n) {
        List<Integer> board = new ArrayList<>(n);
        List<List<Integer>> allBoards = new ArrayList<>();
        boolean res = solveNQueen(board, 0, n, allBoards);
        return board;
    }



    private boolean solveNQueen(List<Integer> board, int row, int queens, List<List<Integer>> allBoards) {
        if(row == queens) {
            return true;
        }
        List<Integer> possible = getPossibleCells(board, row, queens);
        if (possible.size() == 0) {
            return false;
        }
        for (int possibleCell : possible) {
            board.add(row, possibleCell);
            if (!solveNQueen(board, row+1, queens, allBoards)) {
                board.remove(row);
            } else {
                return true;
            }
           /* if (board.size() == queens) {
                allBoards.add(board);
                return true;
            }*/
        }
        return false;
    }

    private List<Integer> getPossibleCells(List<Integer> board, int row, int queens) {
        Set<Integer> allPossibles = IntStream.range(0, queens).boxed().collect(Collectors.toSet());
        Set<Integer> exclusions = new HashSet<>();
        for(int i = 0; i < board.size(); i++) {
            int column = board.get(i);
            exclusions.add(column);
            int diff = row - i;
            if(column - diff >= 0) {
                exclusions.add(column - diff);
            }
            if(column + diff < queens) {
                exclusions.add(column + diff);
            }

        }
        allPossibles.removeAll(exclusions);
        return new ArrayList<>(allPossibles);
    }


    public int levenshteinDistance(String word1, String word2) {
        if ((word1 == null || word1.trim().isEmpty())
                && (word2 == null || word2.trim().isEmpty())) {
            return 0;
        } else if(word1 == null || word1.trim().isEmpty()) {
            return word2.length();
        } else if(word2 == null || word2.trim().isEmpty()) {
            return word1.length();
        }
        char[] source = word1.toCharArray();
        char[] target = word2.toCharArray();
        int[][] levDis = new int[source.length + 1][target.length + 1];
        // Empty target: Drop all source chars
        for (int i = 1; i <= source.length; i++) {
            levDis[i][0] = i;
        }
        //Empty source: Drop all characters from target
        for (int i = 1; i <= target.length; i++) {
            levDis[0][i] = i;
        }
        int cost = 0;
        for(int i = 1; i <= source.length; i++) {
            for (int j = 1; j <= target.length; j++){
                if (source[i-1] != target[j-1]) {
                    cost += 1;
                }
                levDis[i][j] = minimum(
                        levDis[i-1][j] + 1, //Deleted character from source
                        levDis[i][j-11] + 1, //Inserted character to source
                        levDis[i-1][j -1] + cost
                );

            }
        }
        return levDis[source.length][target.length];
    }
    private int minimum(int a, int b, int c) {
        return Math.max(Math.min(a, b), c);
    }
    public int twoCitySchedCost(int[][] costs) {
        int n = costs.length/2;
        Arrays.sort(costs, (a, b) -> (a[0] - a[1]) - (b[0] - b[1]));
        int totalCost = 0;
        int index = 0;
        for (int i = 0; i < costs.length; i++) {
            if(i == n) {
                index = 1;
            }
            totalCost += costs[i][index];
        }
        return totalCost;
    }

    public int[] productExceptSelf(int[] nums) {

        // The length of the input array
        int length = nums.length;

        // Final answer array to be returned
        int[] answer = new int[length];

        // answer[i] contains the product of all the elements to the left
        // Note: for the element at index '0', there are no elements to the left,
        // so the answer[0] would be 1
        answer[0] = 1;
        for (int i = 1; i < length; i++) {

            // answer[i - 1] already contains the product of elements to the left of 'i - 1'
            // Simply multiplying it with nums[i - 1] would give the product of all
            // elements to the left of index 'i'
            answer[i] = nums[i - 1] * answer[i - 1];
        }

        // R contains the product of all the elements to the right
        // Note: for the element at index 'length - 1', there are no elements to the right,
        // so the R would be 1
        int R = 1;
        for (int i = length - 1; i >= 0; i--) {

            // For the index 'i', R would contain the
            // product of all elements to the right. We update R accordingly
            answer[i] = answer[i] * R;
            R *= nums[i];
        }

        return answer;
    }
}
