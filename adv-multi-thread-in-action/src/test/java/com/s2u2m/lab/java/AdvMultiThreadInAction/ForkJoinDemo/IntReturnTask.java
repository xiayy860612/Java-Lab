package com.s2u2m.lab.java.AdvMultiThreadInAction.ForkJoinDemo;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

@Slf4j
public class IntReturnTask extends RecursiveTask<Integer> {

    private final List<Integer> values;
    public IntReturnTask(List<Integer> values) {
        this.values = values;
    }

    @Override
    protected Integer compute() {
        if (values.size() > 1) {
            int sum = subTasks().stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
            log.info(String.format("Sum: %s", sum));
            return sum;
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.values.get(0);
    }

    private List<ForkJoinTask<Integer>> subTasks() {

        int index = this.values.size() / 2;
        List<Integer> pre = this.values.subList(0, index);
        List<Integer> next = this.values.subList(index, this.values.size());
        return Arrays.asList(
                new IntReturnTask(pre).fork(),
                new IntReturnTask(next).fork()
        );
    }
}
