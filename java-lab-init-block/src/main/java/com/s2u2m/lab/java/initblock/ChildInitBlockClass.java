/*
 *     Copyright (C) 2018  s2u2m
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.s2u2m.lab.java.initblock;

import lombok.Getter;

import java.util.UUID;

/**
 * BaseInitBlockClass create on 19-1-6.
 *
 * @author Amos Xia
 */
@Getter
public class ChildInitBlockClass extends BaseInitBlockClass {

    private static final int num1 = initNum();
    private static final int num2;

    static {
        System.out.println("Child static Init block");
        num2 = 12;
    }

    private static int initNum() {
        System.out.println("Child Static Init when declare");
        return 12;
    }

    private String comment = initComment();
    private int sex = 12;
    {
        System.out.println("Child Init Block");
    }

    protected ChildInitBlockClass(int age) {
        super(age);
        System.out.println("Child Constructor Init");
    }

    private String initComment() {
        System.out.println("Child init when declare variable");
        return UUID.randomUUID().toString();
    }
}
