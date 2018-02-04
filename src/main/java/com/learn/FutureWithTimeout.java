package com.learn;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class FutureWithTimeout {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        IntStream.range(0, 5).forEach(num -> {

            System.out.println("num = " + num);
            Callable<Integer> task = () -> {
                TimeUnit.SECONDS.sleep(2);
                return 1 + num;
            };

            Future<Integer> future = executor.submit(task);
            System.out.println("future isDone? = " + future.isDone());

            try {
                Integer taskResult = future.get(1, TimeUnit.SECONDS);
                System.out.println("future isDone? = " + future.isDone());
                System.out.println("taskResult = " + taskResult);
            } catch (Exception e) {
                e.printStackTrace();

            }
        });
    }
}
