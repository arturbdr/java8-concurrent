package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.stream.IntStream;

@Slf4j
public class FutureWithTimeout {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        IntStream.range(0, 5).forEach(num -> {

            log.info("num = {}", num);
            Callable<Integer> task = () -> {
                TimeUnit.SECONDS.sleep(2);
                return 1 + num;
            };

            Future<Integer> future = executor.submit(task);
            log.info("future isDone? = {}", future.isDone());

            try {
                Integer taskResult = future.get(1, TimeUnit.SECONDS);
                log.info("future isDone? = {}", future.isDone());
                log.info("taskResult = {} ", taskResult);
            } catch (Exception e) {
                log.error("failed", e);

            }
        });
    }
}
