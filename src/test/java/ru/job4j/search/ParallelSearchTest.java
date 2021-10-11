package ru.job4j.search;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParallelSearchTest {

    @Test
    public void whenSearchIndexInStringsArray() {
        String[] array = new String[100];
        for (int i = 0; i < 100; i++) {
            array[i] = "String №" + i;
        }
        String object = "String №59";
        int result = ParallelSearch.search(array, object);
        assertThat(result, is(59));
    }

    @Test
    public void whenSearchIndexInIntegersArray() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i;
        }
        int object = 87;
        int result = ParallelSearch.search(array, object);
        assertThat(result, is(87));
    }
}