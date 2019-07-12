package com.s2u2m.lab.java.oom;

public class OomExceptionAnalysis {

    public static void main(String[] args) {
        final int size = 3 * 1024 * 1024;
        byte[] bs1 = new byte[size];
        byte[] bs2 = new byte[size];
        byte[] bs3 = new byte[size];
        byte[] bs4 = new byte[size];
    }
}
