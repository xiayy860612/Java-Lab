package com.s2u2m.example.java.AdvMultiThreadInAction.ExecutorServiceDemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

@Slf4j
public class ExecutorServiceDemoTest {

    @Test
    public void demo1() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 1; i < 1000; ++i) {
            final int value = i;
            Callable<Integer> task = () -> {
                Thread.sleep(100);
                log.info(MessageFormat.format("{0}", value));
                return value;
            };

            futures.add(executor.submit(task));
        }

        executor.shutdown();

        while(!executor.isTerminated()) {
            Thread.sleep(500);
            log.info("Waiting");
        }

        log.info("Done");
    }

}