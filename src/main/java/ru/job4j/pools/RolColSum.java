package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            Sums sums = new Sums();
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums.setRowSum(rowSum);
            sums.setColSum(colSum);
            rowSum = 0;
            colSum = 0;
            result[i] = sums;
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = getTask(matrix, i).get();
        }
        return result;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int row) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            Sums result = new Sums();
            for (int i = 0; i < matrix.length; i++) {
                rowSum += matrix[row][i];
                colSum += matrix[i][row];
            }
            result.setRowSum(rowSum);
            result .setColSum(colSum);
            return result;
        });
    }
}