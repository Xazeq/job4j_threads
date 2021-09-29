package ru.job4j.queue;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenOffer5ThenPoll5() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        AtomicInteger result = new AtomicInteger();
        Thread offerThread = new Thread(
                () -> {
                    try {
                        queue.offer(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        Thread pollThread = new Thread(
                () -> {
                    try {
                        result.set(queue.poll());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        offerThread.start();
        pollThread.start();
        offerThread.join();
        pollThread.join();
        assertThat(result.get(), is(5));
    }

    @Test
    public void whenOffer10ElementsAndPoll10ElementsThenSize0() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread offerThread = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        Thread pollThread = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            queue.poll();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        offerThread.start();
        pollThread.start();
        offerThread.join();
        pollThread.join();
        assertThat(queue.getSize(), is(0));
    }
}