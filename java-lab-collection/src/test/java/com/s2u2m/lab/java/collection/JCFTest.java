package com.s2u2m.lab.java.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * JCFTest
 * Create by Yangyang.xia on 11/29/18
 */
public class JCFTest {

    @Test
    public void test() {
        List<String> view = Arrays.asList("test", "123");
        System.out.println(view);
        List<String> unmodifiedView = Collections.unmodifiableList(view);
        view.set(0, "qwerwer");
//        unmodifiedView.set(0, "123213");
        System.out.println(unmodifiedView);
    }
}
