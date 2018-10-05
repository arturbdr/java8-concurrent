package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;

@Slf4j
public class RacingConditionFixedWithAtomicInteger {
    private final ExecutorService executor = Executors.newFixedThreadPool(50);
    private AtomicInteger number = new AtomicInteger(0);
    private Integer commonInt = 0;

    public static void main(String[] args) {

        RacingConditionFixedWithAtomicInteger conditionFixedWithReadWriteLock = new RacingConditionFixedWithAtomicInteger();
        conditionFixedWithReadWriteLock.test();
    }

    private void test() {

        IntStream.range(0, 100000).forEach(i -> executor.submit(() -> {
            number.addAndGet(1);
            commonInt++;

        }));

        Runnable readTask = () -> {
            log.info("number.get() {}", number.get());
            log.info("commonInt = {}", commonInt);
        };

        executor.submit(readTask);
        executor.submit(readTask);

        stop(executor);

    }

}
