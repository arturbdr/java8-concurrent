package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;

@Slf4j
public class RacingConditionFixedSyncronized {
    private int count = 0;

    public static void main(String[] args) {

        RacingConditionFixedSyncronized instance = new RacingConditionFixedSyncronized();
        instance.test();
    }

    private void test() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        IntStream.range(0, 100000)
                .forEach(i -> executor.submit(this::increment));

        stop(executor);

        log.info("count {}", count); // No more random number. It will print 100000 but with a slower performance
    }

    private synchronized void increment() {
        this.count++;
    }

}
