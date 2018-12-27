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

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The type Inner class demo.
 *
 * @author Amos Xia
 */
@Getter
class DemoWithInnerClass {
    private String name;
    private int age;

    private DemoWithInnerClass() {
    }

    @Getter
    @Setter
    class InnerClass {
        InnerClass(String nameValue, int ageValue) {
            name = nameValue;
            age = ageValue;
        }
    }

    @Setter
    @Accessors(chain = true)
    static class Builder {
        private String name;
        private int age;

        DemoWithInnerClass build() {
            DemoWithInnerClass demo = new DemoWithInnerClass();
            demo.name = name;
            demo.age = age;
            return demo;
        }
    }
}
