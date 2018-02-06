package com.learn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;


public class RacingConditionFixedWithAtomicInteger {
    private final ExecutorService executor = Executors.newFixedThreadPool(50);
    private AtomicInteger number = new AtomicInteger(0);
    private Integer comumInt = 0;

    public static void main(String[] args) {

        RacingConditionFixedWithAtomicInteger conditionFixedWithReadWriteLock = new RacingConditionFixedWithAtomicInteger();
        conditionFixedWithReadWriteLock.test();
    }

    private void test() {

        IntStream.range(0, 100000).forEach(i -> executor.submit(() -> {
            number.addAndGet(1);
            comumInt++;

        }));

        Runnable readTask = () -> {
            System.out.println("number.get() " + number.get());
            System.out.println("comumInt = " + comumInt);
        };

        executor.submit(readTask);
        executor.submit(readTask);

        stop(executor);

    }

}
