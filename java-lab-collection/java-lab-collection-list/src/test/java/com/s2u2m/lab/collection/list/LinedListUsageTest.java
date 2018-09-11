package com.s2u2m.lab.collection.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;

/**
 * LinedListUsageTest
 * Create by Yangyang.xia on 9/7/18
 */
public class LinedListUsageTest {
    private final LinkedList<Integer> integers = new LinkedList<>();

    @Before
    public void setUp() {
        integers.add(10);
        integers.add(7);
        System.out.println(String.format("Begin: %s", integers));
    }

    @Test
    public void test__accessElement() {
        Iterator<Integer> iterator = integers.iterator();
        Integer value = iterator.next();
        System.out.println(value);

    }

    @Test
    public void test__addElement() {
        ListIterator<Integer> iterator = integers.listIterator();
        iterator.next();
        iterator.add(9);
        iterator.add(4);

        System.out.println(integers);
    }

    @Test
    public void test__removeElement() {
        ListIterator<Integer> iterator = integers.listIterator();
        iterator.next();
        iterator.remove();
        System.out.println(integers);
        try {
            iterator.remove();
        } catch (IllegalStateException ex) {
            System.out.println(ex);
            System.out.println("Must invoke next/previous before remove");
        }
    }

    @Test
    public void test__setElement() {
        ListIterator<Integer> iterator = integers.listIterator();
        iterator.next();
        iterator.set(3);
        System.out.println(integers);
    }
}
