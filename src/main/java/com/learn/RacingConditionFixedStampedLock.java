package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;

@Slf4j
public class RacingConditionFixedStampedLock {
    private final StampedLock lock = new StampedLock();
    private final ExecutorService executor = Executors.newFixedThreadPool(50);
    private Integer number = 0;

    public static void main(String[] args) {

        RacingConditionFixedStampedLock conditionFixedWithReadWriteLock = new RacingConditionFixedStampedLock();
        conditionFixedWithReadWriteLock.test();
    }

    private void test() {

        IntStream.range(0, 100000).forEach(i -> executor.submit(this::increment));

        Runnable readTask = () -> {
            long stamp = lock.readLock();
            try {
                log.info("Correct number because of lock for reading {}", number);
            } finally {
                lock.unlockRead(stamp);
            }
        };

        executor.submit(readTask);

        log.info("probably wrong because no lock for reading {}", number);
        stop(executor);

    }

    private void increment() {
        long stamp = lock.writeLock();
        this.number++;
        lock.unlockWrite(stamp);
    }

}
