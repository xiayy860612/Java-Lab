package com.s2u2m.lab.java.exception;

import com.s2u2m.lab.java.exception.exception.AbTest2Exception;
import com.s2u2m.lab.java.exception.exception.AbTestException;
import com.s2u2m.lab.java.exception.exception.BaseException;
import com.s2u2m.lab.java.exception.exception.ExtException;

/**
 * ExtClass create on 2018/8/11
 *
 * @author Amos Xia
 */
public class ExtClass extends AbBaseClass {

    @Override
    public void raise1() throws BaseException, AbTest2Exception {
        throw new BaseException();
    }

    @Override
    public void raise2() throws AbTestException, AbTest2Exception {
        throw new AbTest2Exception();
    }
}
