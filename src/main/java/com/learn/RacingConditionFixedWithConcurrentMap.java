package com.learn;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;


public class RacingConditionFixedWithConcurrentMap {
    public static final String NUMBER = "number";
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    ConcurrentHashMap<String, AtomicInteger> concurrentHashMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        RacingConditionFixedWithConcurrentMap instance = new RacingConditionFixedWithConcurrentMap();
        instance.executeIt();


    }

    private void executeIt() {
        IntStream.range(0, 100).forEach(num -> {
            executorService.submit(() -> {
                incrementNumberInMap();
            });
        });

//        Runnable read = () -> {
//            System.out.println("concurrentHashMap = " + concurrentHashMap.get(NUMBER));
//        };
//
//        executorService.submit(read);
        stop(executorService);

    }

    public void incrementNumberInMap() {
        synchronized (this) {
            concurrentHashMap.putIfAbsent(NUMBER, new AtomicInteger(0));
            concurrentHashMap.replace(NUMBER, new AtomicInteger(concurrentHashMap.get(NUMBER).incrementAndGet()));
            System.out.println("concurrentHashMap = " + concurrentHashMap.get(NUMBER));
        }
    }


}
