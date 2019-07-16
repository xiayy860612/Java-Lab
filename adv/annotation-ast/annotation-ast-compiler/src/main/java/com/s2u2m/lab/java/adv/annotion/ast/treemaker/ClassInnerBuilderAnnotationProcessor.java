package com.s2u2m.lab.java.adv.annotion.ast.treemaker;

import com.google.auto.service.AutoService;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AutoService(Processor.class)
public class ClassInnerBuilderAnnotationProcessor extends AbstractProcessor {

    // message is used for logging some info
    Messager messager;
    // filer is used to write class file
    Filer filer;
    // elementUtils is used to get element info
    Elements elementUtils;
    // typeUtils is used to get type info
    Types typeUtils;

    // AST
    JavacTrees trees;
    // treeMaker is used to modify AST
    TreeMaker treeMaker;
    // names is used to create identity of object
    Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();

        trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Stream.of(ClassInnerBuilder.class.getCanonicalName()).collect(Collectors.toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        List<ClassInnerBuilderAnnotatedClass> annotatedClassList = new LinkedList<>();
        for (Element element: roundEnv.getElementsAnnotatedWith(ClassInnerBuilder.class)) {
            // check if a class is annotated
            if (element.getKind() != ElementKind.CLASS) {
                error("Element[{0}] failed: {1} cannot used for {2}",
                        element.getSimpleName(), ClassInnerBuilder.class.getSimpleName(), element.getKind().toString());
                return true;
            }
            annotatedClassList.add(new ClassInnerBuilderAnnotatedClass((TypeElement) element));
        }

        annotatedClassList.forEach(annotatedClass -> annotatedClass.generateCode(trees, treeMaker, names));
        return true;
    }

    private void error(String pattern, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR,
                MessageFormat.format(pattern, args));
    }
}
