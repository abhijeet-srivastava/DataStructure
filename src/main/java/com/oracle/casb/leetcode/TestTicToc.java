package com.oracle.casb.leetcode;

public class TestTicToc {
    public static void main(String[] args) {
        TestTicToc ttt = new TestTicToc();
        ttt.testTicToc();
    }

    private void testTicToc() {
        int[][] moves = {{0,0},{2,0},{1,1},{2,1},{2,2}};
        System.out.printf("Winner : %s\n", tictactoe(moves));
    }

    public String tictactoe(int[][] moves) {
        if (moves == null || moves.length < 3) {
            return "Pending";
        }
        char[][] game = new char[3][3];
        boolean isA = true;
        for (int[] move: moves) {
            if (isA) {
                game[move[0]][move[1]] = 'A';
            } else {
                game[move[0]][move[1]] = 'B';
            }
            isA = isA ? false : true;
        }
        String winner = "tie";
        for (int i = 0; i < 3; i++) {
            if(game[i][0] == game[i][1] && game[i][1] == game[i][2]) {
                winner = game[i][0] + "";
                break;
            }
            if (game[0][i] == game[1][i] && game[1][i] == game[2][i]) {
                winner = game[0][i] + "";
                break;
            }
        }
        if (winner.equals("tie")) {
            if ((game[0][0] == game[1][1] && game[1][1] == game[2][2])
            || (game[0][2] == game[1][1] && game[1][1] == game[2][0])) {
                winner = game[1][1] + "";
            }
        }
        if (winner.equals("tie") && moves.length < 9) {
            winner = "Pending";
        }
        return winner;
    }
}
