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
                () -> queue.offer(5)
        );
        Thread pollThread = new Thread(
                () -> result.set(queue.poll())
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
                        queue.offer(i);
                    }
                }
        );
        Thread pollThread = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        queue.poll();
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