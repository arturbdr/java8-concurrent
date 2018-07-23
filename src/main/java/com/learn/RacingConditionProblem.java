package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;


/**
 * Loosing the correct count due to race condition
 */
@Slf4j
public class RacingConditionProblem {
    private int count = 0;

    public static void main(String[] args) {

        RacingConditionProblem instance = new RacingConditionProblem();
        instance.test();
    }

    private void test() {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        IntStream.range(0, 100000)
                .forEach(i -> executor.submit(this::increment));

        stop(executor);

        log.info("{}", count); // Will print a random number. Usually below 100000 due to racing conditions
    }

    private void increment() {
        this.count++;
    }

}
