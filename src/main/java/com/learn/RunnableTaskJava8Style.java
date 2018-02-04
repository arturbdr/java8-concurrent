package com.learn;

import java.util.concurrent.TimeUnit;

public class RunnableTaskJava8Style {

    public static void main(String[] args) {
        Runnable task = () -> magageThread();

        Runnable run2 = () -> magageThread();

        new Thread(task).start();
        new Thread(run2).start();

        System.out.println("End of main method!");
    }

    static void magageThread() {
        try {
            String name = Thread.currentThread().getName();
            System.out.println("Thread Name moment 1 " + name);
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Thread Name moment 2 " + name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}