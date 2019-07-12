package com.s2u2m.lab.java.adv.annotion.ast;

public class Demo {

    public static void main(String[] args) {
        AnnotationWithAstBuilder instant = new AnnotationWithAstBuilder();
        AnnotationWithAst instance = instant.name("test").build();
        System.out.println(instance.toString());
    }
}
