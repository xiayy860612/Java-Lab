package com.s2u2m.lab.java.adv.annotion.ast.apt;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AutoService(Processor.class)
public class ClassBuilderAnnotationProcessor extends AbstractProcessor {
    // message is used for logging some info
    Messager messager;
    // filer is used to write class file
    Filer filer;
    // elementUtils is used to get element info
    Elements elementUtils;
    // typeUtils is sued to get type info
    Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Stream.of(ClassBuilderInNewFile.class.getCanonicalName()).collect(Collectors.toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        List<ClassBuilderAnnotatedClass> annotatedClassList = new LinkedList<>();
        for (Element element: roundEnv.getElementsAnnotatedWith(ClassBuilderInNewFile.class)) {
            // check if a class is annotated
            if (element.getKind() != ElementKind.CLASS) {
                error("Element[{0}] failed: {1} cannot used for {2}",
                        element.getSimpleName(), ClassBuilderInNewFile.class.getSimpleName(), element.getKind().toString());
                return true;
            }
            annotatedClassList.add(new ClassBuilderAnnotatedClass((TypeElement) element));
        }

        try {
            for (ClassBuilderAnnotatedClass annotatedClass : annotatedClassList) {
                annotatedClass.generateCode(typeUtils, elementUtils, filer);
            }
        } catch (IOException ex) {
            error("Generate {0} file failed: {1}",
                    ClassBuilderInNewFile.class.getSimpleName(), ex);
            return true;
        }
        return true;
    }

    private void error(String pattern, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR,
                MessageFormat.format(pattern, args));
    }
}
