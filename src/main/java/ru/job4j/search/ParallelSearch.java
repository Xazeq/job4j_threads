package ru.job4j.search;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T object;

    public ParallelSearch(T[] array, int from, int to, T object) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.object = object;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return sequentialSearch(from, to);
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, from, mid, object);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, mid + 1, to, object);
        leftSearch.fork();
        rightSearch.fork();
        Integer left = leftSearch.join();
        Integer right = rightSearch.join();
        return left > right ? left : right;
    }

    private int sequentialSearch(int from, int to) {
        int result = -1;
            for (int i = from; i <= to; i++) {
                if (array[i].equals(object)) {
                    result = i;
                    break;
                }
            }
        return result;
    }

    public static <T> int search(T[] array, T object) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelSearch<>(array, 0, array.length - 1, object));
    }

    public static void main(String[] args) {
        String[] array = new String[100];
        for (int i = 0; i < 100; i++) {
            array[i] = "String №" + i;
        }
        String object = "String №0";
        int result = ParallelSearch.search(array, object);
        System.out.println(result);
    }
}
