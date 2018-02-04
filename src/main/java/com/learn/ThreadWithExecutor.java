package com.learn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadWithExecutor {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            try {
                System.out.println("Running thread " + threadName);
                TimeUnit.SECONDS.sleep(2);
                executor.shutdown();
                executor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println("Problem with thread " + threadName);
                e.printStackTrace();
            } finally {
                if (!executor.isTerminated()) {
                    System.out.println("cancel non-finished tasks");
                }
                System.out.println("Shutting down executor for thread " + threadName);
                executor.shutdownNow();
                System.out.println("shutdown finished");
            }
        });
    }
}
