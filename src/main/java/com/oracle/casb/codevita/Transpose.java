package com.oracle.casb.codevita;

public class Transpose {

    public static void main(String[] args) {
        Transpose tp = new Transpose();
        tp.testTranspose();
    }

    private void testTranspose() {
        int[][] matrix = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}, {13,14,15,16}};
        printMatrix(matrix);
        rotateClock(matrix);
        //rotateAntiClock(matrix);

        printMatrix(matrix);
    }

    private void rotateAntiClock(int[][] matrix) {
        int n = matrix.length;
        for(int i = 0; i < n/2; i++) {
            for(int j = i; j < n -i -1; j++) {

                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][n-i-1];
                matrix[j][n-i-1] = matrix[n-i-1][n-j-1];
                matrix[n-i-1][n-j-1] = matrix[n-j-1][i];
                matrix[n-j-1][i] = tmp;
            }
        }
    }

    private void rotateClock(int[][] matrix) {
        int n = matrix.length;
        for(int i = 0; i < n/2; i++) {
            for(int j = i; j < n -i -1; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n-j-1][i];
                matrix[n-j-1][i] = matrix[n-i-1][n-j-1];
                matrix[n-i-1][n-j-1] = matrix[j][n-i-1];
                matrix[j][n-i-1] = tmp;
            }
        }
    }

    private void printMatrix(int[][] matrix) {
        System.out.println("~~~~~~~~~~~~~~~~ Matrix ~~~~~~~~~~~~~~~~~~~~~ ");
        for(int row = 0; row < matrix.length; row++) {
            for(int col = 0; col < matrix[row].length; col++) {
                System.out.printf("%d%c", matrix[row][col], '\t');
            }
            System.out.printf("%c",'\n');
        }
    }
}
