package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadWithExecutor {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            try {
                log.info("Running thread {}", threadName);
                TimeUnit.SECONDS.sleep(2);
                executor.shutdown();
                executor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error(String.format("Problem with thread %s", threadName), e);
            } finally {
                if (!executor.isTerminated()) {
                    log.info("cancel non-finished tasks");
                }
                log.info("Shutting down executor for thread ", threadName);
                executor.shutdownNow();
                log.info("shutdown finished");
            }
        });
    }
}
