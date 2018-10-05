package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RunnableTaskJava8Style {

    public static void main(String[] args) {
        Runnable task = RunnableTaskJava8Style::manageThread;

        Runnable run2 = RunnableTaskJava8Style::manageThread;

        new Thread(task).start();
        new Thread(run2).start();

        log.info("End of main method!");
    }

    static void manageThread() {
        try {
            String name = Thread.currentThread().getName();
            log.info("Thread Name moment 1 {}", name);
            TimeUnit.SECONDS.sleep(1);
            log.info("Thread Name moment 2 {}", name);
        } catch (InterruptedException e) {
            log.error("failed", e);
        }
    }
}