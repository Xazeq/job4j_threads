package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenSeqSumInRow1Col1Then6And12() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] array = RolColSum.sum(matrix);
        int rowSum = array[0].getRowSum();
        int colSum = array[0].getColSum();
        assertThat(rowSum, is(6));
        assertThat(colSum, is(12));
    }

    @Test
    public void whenSeqSumInRow2Col2Then15And15() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] array = RolColSum.sum(matrix);
        int rowSum = array[1].getRowSum();
        int colSum = array[1].getColSum();
        assertThat(rowSum, is(15));
        assertThat(colSum, is(15));
    }

    @Test
    public void whenSeqSumInRow3Col3Then24And18() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] array = RolColSum.sum(matrix);
        int rowSum = array[2].getRowSum();
        int colSum = array[2].getColSum();
        assertThat(rowSum, is(24));
        assertThat(colSum, is(18));
    }

    @Test
    public void whenAsyncSumInRow1Col1Then6And12() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] array = RolColSum.asyncSum(matrix);
        int rowSum = array[0].getRowSum();
        int colSum = array[0].getColSum();
        assertThat(rowSum, is(6));
        assertThat(colSum, is(12));
    }

    @Test
    public void whenAsyncSumInRow2Col2Then15And15() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] array = RolColSum.asyncSum(matrix);
        int rowSum = array[1].getRowSum();
        int colSum = array[1].getColSum();
        assertThat(rowSum, is(15));
        assertThat(colSum, is(15));
    }

    @Test
    public void whenAsyncSumInRow3Col3Then24And18() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] array = RolColSum.asyncSum(matrix);
        int rowSum = array[2].getRowSum();
        int colSum = array[2].getColSum();
        assertThat(rowSum, is(24));
        assertThat(colSum, is(18));
    }
}