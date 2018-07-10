package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.stream.IntStream;

@Slf4j
public class CallableExample {

    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(num -> {

            log.info("num {}", num);

            Callable<Integer> callableResult = () -> num + 1;

            try {

                log.info("task = {}", callableResult.call());
            } catch (Exception e) {
                log.error("error", e);
            }
        });


    }
}
