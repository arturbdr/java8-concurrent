package com.learn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;


/**
 * Loosing the correct count due to race condition
 */
public class Executor {
    private int count = 0;

    public static void main(String[] args) {

        Executor instance = new Executor();
        instance.test();
    }

    private void test() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 100000)
                .forEach(i -> executor.submit(this::increment));

        stop(executor);

        System.out.println(count);
    }

    private void increment() {
        this.count++;
    }

}
