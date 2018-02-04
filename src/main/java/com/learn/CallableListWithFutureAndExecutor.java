package com.learn;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableListWithFutureAndExecutor {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> {
                    return "task 1";
                },
                () -> {
                    return "task 2";
                },
                () -> {
                    return "task 3";
                });

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
                .forEach(future -> System.out.println("functional future = " + future));


        // Less functional
        List<Future<String>> futures = executor.invokeAll(callables);
        futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "error!";
                    }
                })
                .forEach(future -> System.out.println("future = " + future));
    }
}
