package com.walmart.graph;

import org.bouncycastle.util.Arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NQueen {

    public static void main(String[] args) {
        NQueen nq = new NQueen();
        //String sol = IntStream.of(nq.getSolutionBoard(4)).mapToObj(String::valueOf).collect(Collectors.joining(","));
        //System.out.println(sol);
        List<List<String>> results = nq.solveNQueens(10);
        for(int i = 1; i <= results.size(); i++) {
            System.out.printf("Solution#%d = %s\n", i,results.get(i-1).stream().collect(Collectors.joining(", ", "[", "]")));
        }
    }

    public List<List<String>>  solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        int[] board = new int[n];
        Arrays.fill(board, -1);
        fillNextQueen(0, board, result, n);
        return result;
    }
    private void fillNextQueen(int row, int[] board,  List<List<String>> result, int n) {
        if(row < 0) {
            return;
        } else if(row == n) {
            result.add(printBoard(board, n));
            return;
        }
        List<Integer> safePositions = getSafePositions(row, board, n);
        if(safePositions.isEmpty()) {
            board[row] = -1;
            return;
        }
        //boolean res = false;
        for(int safePosition : safePositions) {
            board[row] = safePosition;
            fillNextQueen(row+1, board, result, n);
            board[row] = -1;
        }
        /*if(!res) {
            board[row] = -1;
        }*/
        //return res;
    }


    private List<Integer> getSafePositions(int count, int[] board, int n) {
        List<Integer> safePositions = new ArrayList<>();
        for(int i = board[count]+1; i < n; i++) {
            if(isSafe(count, i, board)) {
                safePositions.add(i);
            }
        }
        return safePositions;
    }

    private List<String> printBoard(int[] sol, int n) {
        List<String> result = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            char[] arr = new char[n];
            Arrays.fill(arr, '.');
            arr[sol[i]] ='Q';
            String solStr = String.valueOf(arr);
            result.add(solStr);
        }
        System.out.printf("Board: \n%s\n", result.stream().collect(Collectors.joining(",   \n")));
        return result;
    }

    public int[] getSolutionBoard(int n) {
        int[] board = new int[n];
        Arrays.fill(board, -1);
        int count =  0;
        while(count < n) {
            int safePosition = getNextSafePositions(count, board, n);
            if(safePosition < 0) {
                board[count] = -1;
                count -= 1;
                if(count < 0) {
                    return new int[0];
                }
                continue;
            } else {
                board[count] = safePosition;
                count += 1;
            }
        }
        return board;
    }

    private int getNextSafePositions(int count, int[] board, int n) {
        int position = -1;
        for(int i = board[count]+1; i < n; i++) {
            if(isSafe(count, i, board)) {
                position = i;
                break;
            }
        }
        return position;
    }

    private boolean isSafe(int row, int col, int[] board) {
        boolean isSafe = true;
        for(int i = row-1; i >= 0; i--) {
            if(board[i] == col
                    || ((row+col) == (i+board[i]))
                    || ((row - col) == (i - board[i]))) {
                isSafe = false;
                break;
            }
        }
        return isSafe;
    }

}
