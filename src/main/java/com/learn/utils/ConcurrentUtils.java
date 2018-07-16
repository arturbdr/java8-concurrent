package com.learn.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConcurrentUtils {

    public static void stop(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("Termination Interrupted", e);
        } finally {
            if (!executor.isTerminated()) {
                log.warn("killing non-finished tasks");
            }
            executor.shutdownNow();
        }
    }

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void sleep(int value, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(value);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
