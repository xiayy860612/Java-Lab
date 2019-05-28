package com.s2u2m.lab.java.AdvMultiThreadInAction.StreamDemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StreamDemoTest {

    List<Integer> elements = new ArrayList<>();

    @Before
    public void init() {
        elements = new ArrayList<>();
        for (int i = 1; i < 100; ++i) {
            elements.add(i);
        }
    }

    @Test
    public void stream() {
        int sum = elements.parallelStream().mapToInt(element -> {
            log.info(String.format("%s", element));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return element;
        }).sum();
        log.info(String.format("%s", sum));
    }
}
