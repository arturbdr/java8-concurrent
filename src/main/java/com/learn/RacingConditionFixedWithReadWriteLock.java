package com.learn;

import lombok.extern.slf4j.Slf4j;
import sun.jvm.hotspot.utilities.Assert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.sleep;
import static com.learn.utils.ConcurrentUtils.stop;

@Slf4j
public class RacingConditionFixedWithReadWriteLock {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ExecutorService executor = Executors.newFixedThreadPool(1000);
    private Integer number = 0;

    public static void main(String[] args) {

        RacingConditionFixedWithReadWriteLock conditionFixedWithReadWriteLock = new RacingConditionFixedWithReadWriteLock();
        conditionFixedWithReadWriteLock.test();
    }

    private void test() {

        IntStream.range(0, 10000)
                .forEach(i -> executor.submit(() -> {
                    lock.writeLock().lock();
                    sleep(1, TimeUnit.MILLISECONDS);
                    try {
                        number++;
                    } finally {
                        lock.writeLock().unlock();

                    }
                }));

        Runnable readTask = () -> {
            lock.readLock().lock();
            try {
                log.info("with lock number {}", number);
            } finally {
                lock.readLock().unlock();
            }
        };

        Runnable readTaskMightFailWithoutReadLock = () -> log.info("without lock number {}", number);

        executor.submit(readTask);
        executor.submit(readTaskMightFailWithoutReadLock);

        stop(executor);
        Assert.that(number == 10000, "Racing condition failed " + number);

    }

}
