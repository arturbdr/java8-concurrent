package com.learn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;


public class RacingConditionFixedStampedLock {
    private final StampedLock lock = new StampedLock();
    private final ExecutorService executor = Executors.newFixedThreadPool(50);
    private Integer number = 0;

    public static void main(String[] args) {

        RacingConditionFixedStampedLock conditionFixedWithReadWriteLock = new RacingConditionFixedStampedLock();
        conditionFixedWithReadWriteLock.test();
    }

    private void test() {

        IntStream.range(0, 100000).forEach(i -> executor.submit(() -> {
            long stamp = lock.writeLock();
            number++;
            lock.unlockWrite(stamp);

        }));

        Runnable readTask = () -> {
            long stamp = lock.readLock();
            try {
                System.out.println(number);
            } finally {
                lock.unlockRead(stamp);
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);

        System.out.println("probably wrong " + number);
        stop(executor);

    }

}
