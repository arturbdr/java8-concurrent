package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;

@Slf4j
public class CallableWithFuture {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        IntStream.range(0, 5)
                .forEach(num -> {

                    log.info("num = {}", num);
                    Callable<Integer> task = () -> {
                        return 1 + num;
                    };

                    Future<Integer> future = executor.submit(task);
                    log.info("future isDone? = {}", future.isDone());

                    try {
                        Integer taskResult = future.get();
                        log.info("future isDone? = {} ", future.isDone());
                        log.info("taskResult = {}", taskResult);
                    } catch (Exception e) {
                        log.error("failed", e);

                    } finally {
                        stop(executor);
                    }
                });
    }
}
