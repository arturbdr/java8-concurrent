package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class CallableListWithFutureAndExecutor {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> "task 1",
                () -> "task 2",
                () -> "task 3");

        // All instructions as functional
        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(future -> log.info("functional future = {}", future));


        // Less functional
        List<Future<String>> futures = executor.invokeAll(callables);
        futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        log.error("failed", e);
                        return "error!";
                    }
                })
                .forEach(future -> log.info("future = {}", future));
    }
}
