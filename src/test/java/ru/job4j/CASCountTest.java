package ru.job4j;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void when2ThreadsIncreaseBy5ThenResult10() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        count.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        count.increment();
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(10));
    }

    @Test
    public void when3ThreadsIncreaseBy10ThenResult30() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        count.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        count.increment();
                    }
                }
        );
        Thread third = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        count.increment();
                    }
                }
        );
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertThat(count.get(), is(30));
    }
}