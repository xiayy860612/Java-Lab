package com.s2u2m.lab.java.AdvMultiThreadInAction.ExecutorServiceDemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@Slf4j
public class ExecutorServiceDemoTest {

    List<Callable<Integer>> tasks = new ArrayList<>();

    @Before
    public void init() {
        tasks = new ArrayList<>();
        for (int i = 1; i < 100; ++i) {
            final int value = i;
            Callable<Integer> task = () -> {
                Thread.sleep(100);
                log.info(MessageFormat.format("{0}", value));
                return value;
            };

            tasks.add(task);
        }
    }

    @Test
    public void demo_not_wait_for_result() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        List<Future<Integer>> futures = tasks.stream()
                .map(executor::submit)
                .collect(Collectors.toList());

//        executor.shutdown();
//        boolean isDone = executor.isTerminated();
        boolean isDone = futures.stream().allMatch(Future::isDone);
        while(!isDone) {
            log.info("Waiting");
            Thread.sleep(500);
            isDone = futures.stream().allMatch(Future::isDone);
        }
        log.info("Done");
        executor.shutdownNow();
    }

    @Test
    public void demo_wait_for_result() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<Integer>> futures = executor.invokeAll(tasks);
//        executor.shutdown();
//        boolean isDone = executor.isTerminated();
        boolean isDone = futures.stream().allMatch(Future::isDone);
        assertTrue(isDone);
        log.info("Done");
        executor.shutdownNow();
    }

}