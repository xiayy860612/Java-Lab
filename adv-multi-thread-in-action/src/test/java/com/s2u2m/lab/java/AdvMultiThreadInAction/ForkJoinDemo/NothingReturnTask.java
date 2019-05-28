package com.s2u2m.lab.java.AdvMultiThreadInAction.ForkJoinDemo;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

@Slf4j
public class NothingReturnTask extends RecursiveAction {

    private final List<Integer> values;
    public NothingReturnTask(List<Integer> values) {
        this.values = values;
    }

    @Override
    protected void compute() {
        if (values.size() > 1) {
            ForkJoinTask.invokeAll(subTasks());
            return;
        }

        try {
            Thread.sleep(100);
            log.info(String.format("%s", this.values.get(0)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<NothingReturnTask> subTasks() {
        int index = this.values.size() / 2;
        List<Integer> pre = this.values.subList(0, index);
        List<Integer> next = this.values.subList(index, this.values.size());
        return Arrays.asList(
                new NothingReturnTask(pre),
                new NothingReturnTask(next)
        );
    }
}
