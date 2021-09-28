package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static class Use {
        public static void main(String[] args) throws InterruptedException {
            CountBarrier countBarrier = new CountBarrier(5);
            Thread threadCount = new Thread(
                    () -> {
                        System.out.println(Thread.currentThread().getName() + " started");
                        for (int i = 0; i < 10; i++) {
                            countBarrier.count();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + " ends");
                    }, "count thread"
            );
            Thread threadAwait = new Thread(
                    () -> {
                        System.out.println(Thread.currentThread().getName() + " started");
                        countBarrier.await();
                        System.out.println(Thread.currentThread().getName() + " ends");
                    }, "await thread"
            );
            threadCount.start();
            threadAwait.start();
            threadCount.join();
            threadAwait.join();
        }
    }
}
