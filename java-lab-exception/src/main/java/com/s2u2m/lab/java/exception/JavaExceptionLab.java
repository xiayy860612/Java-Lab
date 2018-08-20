package com.s2u2m.lab.java.exception;

import com.s2u2m.lab.java.exception.exception.AbTest2Exception;
import com.s2u2m.lab.java.exception.exception.AbTestException;
import com.s2u2m.lab.java.exception.exception.BaseException;
import com.s2u2m.lab.java.exception.exception.ExtException;

/**
 * JavaExceptionLab create on 2018/8/11
 *
 * @author Amos Xia
 */
public class JavaExceptionLab {

    public static void main(String[] args) throws Throwable {
        AbBaseClass abBaseClass = new ExtClass();

        try {
            abBaseClass.raise1();
        } catch (BaseException e) {
            System.out.println("Orig: " + e.getClass().getName());
            e = new ExtException();
            System.out.println("Update: " + e.getClass().getName());
        }

        try {
            abBaseClass.raise2();
        } catch (AbTestException | AbTest2Exception e) {
            System.out.println("Orig: " + e.getClass().getName());
            // cannot update, it's final
            // e = new ExtException();
            // System.out.printf("Update: " + e.getClass().getName());
        } catch (Exception e) {
            Throwable ex = new Exception();
            ex.initCause(e);
            throw ex;
        }

        TestResource testResource = new TestResource();
        try {
            System.out.printf("user resource");
        } catch (Exception e) {

        } finally {
            try {
                testResource.close();
            } catch (Exception e) {
//                throw e;
            } finally {

            }
        }

        try(TestResource resource = new TestResource()) {
            System.out.println("use resource");
            throw new AbTestException();
        } catch (AbTestException e) {
            System.out.println("catch ex");
        } finally {
            System.out.println("final");
        }

    }
}
