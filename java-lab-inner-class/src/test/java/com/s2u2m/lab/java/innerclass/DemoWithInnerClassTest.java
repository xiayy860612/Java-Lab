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

package com.s2u2m.lab.java.innerclass;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * DemoWithInnerClassTest create on 18-12-27.
 *
 * @author Amos Xia
 */
public class DemoWithInnerClassTest {

    @Test
    public void testInnerClassAccessOuterClass() {
        String expName = "test";
        int expAge = 20;
        int expAge2 = 30;

        // 静态内部类可以直接构造， 而不需要依赖外部类的实例
        DemoWithInnerClass.Builder builder = new DemoWithInnerClass.Builder()
                .setName(expName)
                .setAge(expAge);

        DemoWithInnerClass outerClass = builder.build();
        assertEquals(expName, outerClass.getName());
        assertEquals(expAge, outerClass.getAge());

        // 必须使用外部类的实例来构造内部类， 因为内部类需要引用外部类实例
        DemoWithInnerClass.InnerClass innerClass = outerClass.new InnerClass(expName, expAge2);

        assertEquals(expName, outerClass.getName());
        assertEquals(expAge2, outerClass.getAge());
    }
}