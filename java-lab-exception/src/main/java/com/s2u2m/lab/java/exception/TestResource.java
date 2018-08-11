package com.s2u2m.lab.java.exception;

/**
 * TestResource create on 2018/8/11
 *
 * @author Amos Xia
 */
public class TestResource implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.println("TestResource close");
    }
}
