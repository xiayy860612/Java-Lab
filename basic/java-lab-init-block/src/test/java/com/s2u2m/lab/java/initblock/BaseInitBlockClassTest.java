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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * BaseInitBlockClassTest create on 19-1-6.
 *
 * @author Amos Xia
 */
public class BaseInitBlockClassTest {

    final static int expectAge = 20;

    @Test
    public void testInitBlock() {
        BaseInitBlockClass testClass = new BaseInitBlockClass(expectAge) {
        };

        assertEquals(expectAge, testClass.getAge());
    }

    @Test
    public void testInitBlockInherit() {
        ChildInitBlockClass child = new ChildInitBlockClass(expectAge);
        assertEquals(expectAge, child.getAge());
    }
}