package com.learn;

import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class CallableExample {

    public static void main(String[] args) {
        IntStream.range(0,10).forEach(num -> {

            System.out.println("num = " + num);

            Callable<Integer> callableResult = () -> {
                return num + 1;
            };

            try {
                System.out.println("task = " + callableResult.call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }
}
