package com.s2u2m.lab.java.exception;

import com.s2u2m.lab.java.exception.exception.AbTest2Exception;
import com.s2u2m.lab.java.exception.exception.AbTestException;
import com.s2u2m.lab.java.exception.exception.BaseException;

/**
 * AbBaseClass create on 2018/8/11
 *
 * @author Amos Xia
 */
public abstract class AbBaseClass {

    public abstract void raise1() throws BaseException;
    public abstract void raise2() throws AbTestException, AbTest2Exception;
}
