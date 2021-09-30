package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);

    public ThreadPool() {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(new Worker());
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (var thread : threads) {
            thread.interrupt();
        }
    }

    private final class Worker implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 1; i <= 100; i++) {
            int number = i;
            threadPool.work(
                    () -> System.out.println("Task №" + number + " completed, thread name: "
                            + Thread.currentThread().getName())
            );
        }
        threadPool.shutdown();
    }
}
