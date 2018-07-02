package com.learn;

import static com.learn.utils.ConcurrentUtils.sleep;
import static com.learn.utils.ConcurrentUtils.stop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;


public class RacingConditionFixedWithReadWriteLock {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ExecutorService executor = Executors.newFixedThreadPool(100);
    private Integer number = 0;

    public static void main(String[] args) {

        RacingConditionFixedWithReadWriteLock conditionFixedWithReadWriteLock = new RacingConditionFixedWithReadWriteLock();
        conditionFixedWithReadWriteLock.test();
    }

    private void test() {

        IntStream.range(0, 10000)
                .forEach(i -> executor.submit(() -> {
                    lock.writeLock().lock();
                    sleep(10, TimeUnit.MILLISECONDS);
                    try {
                        number++;
                    } finally {
                        lock.writeLock().unlock();

                    }
                }));

        Runnable readTask = () -> {
            lock.readLock().lock();
            try {
                System.out.println(number);
            } finally {
                lock.readLock().unlock();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);

        stop(executor);

    }

}
