package com.s2u2m.lab.java.adv.annotion.ast.apt;

import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ClassBuilderAnnotatedClass {
    private static final String SUFFIX = "Builder";

    private TypeElement typeElement;

    public ClassBuilderAnnotatedClass(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public void generateCode(Types typeUtils, Elements elementUtils, Filer filer) throws IOException {
        PackageElement packageElement = elementUtils.getPackageOf(typeElement);
        String packageName = packageElement.isUnnamed() ? null : packageElement.getQualifiedName().toString();
        ClassName builderType = ClassName.get(packageName, typeElement.getSimpleName().toString() + SUFFIX);
        TypeSpec.Builder builder = TypeSpec.classBuilder(builderType);

        List<VariableElement> fields = ElementFilter.fieldsIn(
                elementUtils.getAllMembers(typeElement)).stream()
                .filter(this::nonPrivateFieldFilter)
                .collect(Collectors.toList());
        fields.forEach(field -> this.createField(builderType, field, builder));
        this.createBuilderMethod(builder, fields);

        JavaFile.builder(builderType.packageName(), builder.build())
                .build().writeTo(filer);
    }

    private boolean nonPrivateFieldFilter(VariableElement field) {
        return field.getKind() == ElementKind.FIELD
                && !field.getModifiers().contains(Modifier.PRIVATE);
    }

    private void createField(ClassName builderType, VariableElement field, TypeSpec.Builder builder) {
        String fieldName = field.getSimpleName().toString();
        TypeName fieldType = TypeName.get(field.asType());

        FieldSpec fieldSpec = FieldSpec.builder(fieldType, fieldName)
                .addModifiers(Modifier.PRIVATE)
                .build();

        MethodSpec methodSpec = MethodSpec.methodBuilder(fieldName)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(fieldType, fieldName)
                .addStatement("this.$1N = $1N", fieldName)
                .addStatement("return this")
                .returns(builderType)
                .build();

        builder.addField(fieldSpec);
        builder.addMethod(methodSpec);
    }

    public void createBuilderMethod(TypeSpec.Builder builder, List<VariableElement> fields) {
        String className = this.typeElement.getSimpleName().toString();
        String instanceName = "instance";
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$1N $2N = new $1N()", className, instanceName)
                .returns(TypeName.get(this.typeElement.asType()));

        for (VariableElement field : fields) {
            String fieldName = field.getSimpleName().toString();
            methodBuilder.addStatement("$1N.$2N = this.$2N", instanceName, fieldName);
        }

        methodBuilder.addStatement("return $N", instanceName);
        builder.addMethod(methodBuilder.build());
    }
}
