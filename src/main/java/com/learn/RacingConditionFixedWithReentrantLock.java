package com.learn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;


public class RacingConditionFixedWithReentrantLock {
    ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    public static void main(String[] args) {

        RacingConditionFixedWithReentrantLock instance = new RacingConditionFixedWithReentrantLock();
        instance.test();
    }

    private void test() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        IntStream.range(0, 1000)
                .forEach(i -> executor.submit(this::increment));

        stop(executor);

        System.out.println(count);
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
