package com.learn;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.sleep;
import static com.learn.utils.ConcurrentUtils.stop;


public class RacingConditionFixedWithReadWriteLock {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<String, Integer> map = new HashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {

        RacingConditionFixedWithReadWriteLock conditionFixedWithReadWriteLock = new RacingConditionFixedWithReadWriteLock();
        conditionFixedWithReadWriteLock.test();
    }

    private void test() {

        IntStream.range(0, 5)
                .forEach(i -> executor.submit(() -> {
                    lock.writeLock().lock();
                    try {
                        sleep(1);

                        map.merge("foo", i, (a, b) -> a + b);

                    } finally {
                        lock.writeLock().unlock();

                    }
                }));

        Runnable readTask = () -> {
            lock.readLock().lock();
            try {
                System.out.println(map.get("foo"));
                sleep(1);
            } finally {
                lock.readLock().unlock();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);

        stop(executor);

    }

}
