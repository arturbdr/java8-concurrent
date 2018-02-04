package com.learn;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;

public class CallableWithFuture {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        IntStream.range(0, 5)
                .forEach(num -> {

                    System.out.println("num = " + num);
                    Callable<Integer> task = () -> {
                        return 1 + num;
                    };

                    Future<Integer> future = executor.submit(task);
                    System.out.println("future isDone? = " + future.isDone());

                    try {
                        Integer taskResult = future.get();
                        System.out.println("future isDone? = " + future.isDone());
                        System.out.println("taskResult = " + taskResult);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    finally {
                        stop(executor);
                    }
                });
    }
}
