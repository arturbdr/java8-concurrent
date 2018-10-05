package com.learn;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import static com.learn.utils.ConcurrentUtils.stop;

@Slf4j
public class RacingConditionFixedWithConcurrentMap {

    private static final String NUMBER = "number";

    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private ConcurrentHashMap<String, LongAdder> concurrentHashMap = new ConcurrentHashMap<>();
    private HashMap<String, LongAdder> hashMap = new HashMap<>();

    public static void main(String[] args) {
        RacingConditionFixedWithConcurrentMap instance = new RacingConditionFixedWithConcurrentMap();
        instance.executeIt();
    }

    private void executeIt() {
        IntStream.range(0, 100000).forEach(num -> executorService.submit(() -> incrementNumberInMap(num)));

        Runnable read = () -> {
            log.info("concurrentHashMap sum  = {}", concurrentHashMap.get(NUMBER));
            log.info("concurrentHashMap size = {}", concurrentHashMap.size());
            log.info("commonMap sum  = {}", hashMap.get(NUMBER));
            log.info("commonMap size = {}", hashMap.size());
        };

        executorService.submit(read);
        stop(executorService);

    }

    private void incrementNumberInMap(Integer num) {
        concurrentHashMap.computeIfAbsent(NUMBER, k -> new LongAdder()).increment();
        concurrentHashMap.put(NUMBER + num.toString(), new LongAdder());

        hashMap.computeIfAbsent(NUMBER, k -> new LongAdder()).increment();
        hashMap.put(NUMBER + num.toString(), new LongAdder());

    }


}
