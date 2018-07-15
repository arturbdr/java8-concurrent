package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;

@Slf4j
public class RacingConditionFixedWithConcurrentMap {

    private static final String NUMBER = "number";

    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private ConcurrentHashMap<String, AtomicInteger> concurrentHashMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        RacingConditionFixedWithConcurrentMap instance = new RacingConditionFixedWithConcurrentMap();
        instance.executeIt();
    }

    private void executeIt() {
        IntStream.range(0, 100).forEach(num -> executorService.submit(this::incrementNumberInMap));

        Runnable read = () -> log.info("concurrentHashMap = {}", concurrentHashMap.get(NUMBER));

        executorService.submit(read);
        stop(executorService);

    }

    private void incrementNumberInMap() {
        synchronized (this) {
            concurrentHashMap.putIfAbsent(NUMBER, new AtomicInteger(0));
            concurrentHashMap.replace(NUMBER, new AtomicInteger(concurrentHashMap.get(NUMBER).incrementAndGet()));
            log.info("concurrentHashMap = {}", concurrentHashMap.get(NUMBER));
        }
    }


}
