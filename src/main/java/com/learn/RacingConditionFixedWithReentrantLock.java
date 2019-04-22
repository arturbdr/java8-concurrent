package com.learn;

import lombok.extern.slf4j.Slf4j;
import sun.jvm.hotspot.utilities.Assert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;

@Slf4j
public class RacingConditionFixedWithReentrantLock {
    private ReentrantLock lock = new ReentrantLock();
    private Integer count = 0;

    public static void main(String[] args) {

        RacingConditionFixedWithReentrantLock instance = new RacingConditionFixedWithReentrantLock();
        instance.test();
    }

    private void test() {
        ExecutorService executor = Executors.newFixedThreadPool(100);

        IntStream.range(0, 100000)
                .forEach(i -> executor.submit(this::increment));

        stop(executor);

        log.info("count {}", count); // 10000
        Assert.that(100000 == count, "Not equal " + count);
    }

    private void increment() {
        lock.lock();
        try {
            this.count++;
        } finally {
            lock.unlock();
        }


    }

}
