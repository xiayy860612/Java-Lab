package com.s2u2m.lab.java.adv.annotion.ast;

public class Demo {

    public static void main(String[] args) {
        AnnotationWithClassBuilderInNewFileBuilder instant = new AnnotationWithClassBuilderInNewFileBuilder();
        AnnotationWithClassBuilderInNewFile instance = instant.name("test").build();
        System.out.println(instance.toString());

        AnnotationWithClassInnerBuilderClass.Builder builder =
                new AnnotationWithClassInnerBuilderClass.Builder();
        builder.name("xyy");
        System.out.println(builder.build().getName());
    }
}
