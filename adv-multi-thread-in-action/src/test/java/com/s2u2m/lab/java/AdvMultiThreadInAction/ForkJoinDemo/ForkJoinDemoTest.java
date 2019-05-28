package com.s2u2m.lab.java.AdvMultiThreadInAction.ForkJoinDemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

@Slf4j
public class ForkJoinDemoTest {

    List<Integer> elements = new ArrayList<>();

    @Before
    public void init() {
        elements = new ArrayList<>();
        for (int i = 1; i < 100; ++i) {
            elements.add(i);
        }
    }

    @Test
    public void useForkJoinForNothing() throws InterruptedException {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        NothingReturnTask task = new NothingReturnTask(this.elements);
        commonPool.execute(task);
        while (!task.isDone()) {
            log.info("Waiting");
            Thread.sleep(100);
        }

        log.info("Done");
        commonPool.shutdown();
    }

    @Test
    public void userForkJoinForIntSum() throws InterruptedException, ExecutionException {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        IntReturnTask task = new IntReturnTask(this.elements);
        commonPool.execute(task);
        while (!task.isDone()) {
            log.info("Waiting");
            Thread.sleep(100);
        }

        int sum = task.get();
        log.info(String.format("Done: %s", sum));
        commonPool.shutdown();

    }
}
