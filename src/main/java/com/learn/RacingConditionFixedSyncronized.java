package com.learn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;


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

        System.out.println(count); // No more random number. It will print 100000 but with a slower performance
    }

    private synchronized void increment() {
        this.count++;
    }

}
