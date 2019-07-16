package com.s2u2m.lab.java.adv.annotion.ast.treemaker;


import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public class ClassInnerBuilderAnnotatedClass {
    private static final String BUILDER_CLASS_NAME_SUFFIX = "Builder";

    private TypeElement typeElement;

    public ClassInnerBuilderAnnotatedClass(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public void generateCode(JavacTrees trees, TreeMaker treeMaker, Names names) {
        JCTree jcTree = trees.getTree(typeElement);
        jcTree.accept(new TreeClassDefTranslator(trees, treeMaker, names));
    }


    private class TreeClassDefTranslator extends TreeTranslator {

        private JavacTrees trees;
        private TreeMaker treeMaker;
        private Names names;

        public TreeClassDefTranslator(JavacTrees trees, TreeMaker treeMaker, Names names) {
            super();
            this.trees = trees;
            this.treeMaker = treeMaker;
            this.names = names;
        }

        @Override
        public void visitClassDef(JCTree.JCClassDecl jcClass) {
            jcClass.defs = jcClass.defs.append(createInnerBuilderClass(jcClass));
        }

        private List<JCTree.JCVariableDecl> getValidFields(JCTree.JCClassDecl jcClass) {
            ListBuffer<JCTree.JCVariableDecl> jcVariables = new ListBuffer<>();

            for (JCTree jcTree : jcClass.defs) {
                if (jcTree.getKind() != JCTree.Kind.VARIABLE) {
                    continue;
                }

                JCTree.JCVariableDecl jcVariable = (JCTree.JCVariableDecl) jcTree;
                Set<Modifier> flagSets = jcVariable.mods.getFlags();
                if (flagSets.contains(Modifier.STATIC)) {
                    continue;
                }

                jcVariables.append(jcVariable);
            }

            return jcVariables.toList();
        }

        private JCTree.JCClassDecl createInnerBuilderClass(JCTree.JCClassDecl jcClass) {
            ListBuffer<JCTree> jcTrees = new ListBuffer<>();
            List<JCTree.JCVariableDecl> fields = getValidFields(jcClass);
            jcTrees.appendList(this.createBuilderFields(fields));
            jcTrees.append(createBuildMethod(jcClass, fields));

            return treeMaker.ClassDef(
                    treeMaker.Modifiers(Flags.PUBLIC + Flags.STATIC + Flags.FINAL),
                    names.fromString(BUILDER_CLASS_NAME_SUFFIX),
                    List.nil(),
                    null,
                    List.nil(),
                    jcTrees.toList()
            );
        }

        private List<JCTree> createBuilderFields(List<JCTree.JCVariableDecl> fields) {
            ListBuffer<JCTree> jcVariables = new ListBuffer<>();
            ListBuffer<JCTree> setMethods = new ListBuffer<>();

            for (JCTree.JCVariableDecl field : fields) {
                JCTree.JCVariableDecl builderVarDecl = treeMaker.VarDef(
                        treeMaker.Modifiers(Flags.PRIVATE),
                        names.fromString(field.name.toString()),
                        field.vartype,
                        null
                );
                jcVariables.append(builderVarDecl);

                JCTree.JCMethodDecl setMethod = this.createFieldSetMethod(field);
                setMethods.append(setMethod);
            }

            ListBuffer<JCTree> fieldElements = new ListBuffer<>();
            fieldElements.appendList(jcVariables);
            fieldElements.appendList(setMethods);
            return fieldElements.toList();
        }

        private JCTree.JCMethodDecl createFieldSetMethod(JCTree.JCVariableDecl field) {
            Name thisIdName = names.fromString("this");
            JCTree.JCIdent thisId = treeMaker.Ident(thisIdName);
            Name fieldName = names.fromString(field.name.toString());

            ListBuffer<JCTree.JCStatement> jcStatements = new ListBuffer<>();
            JCTree.JCStatement statement = treeMaker.Exec(treeMaker.Assign(
                    treeMaker.Select(thisId, fieldName), treeMaker.Ident(fieldName)));
            jcStatements.append(statement);
            JCTree.JCStatement returnStatement = treeMaker.Return(thisId);
            jcStatements.append(returnStatement);
            JCTree.JCBlock jcBlock = treeMaker.Block(0, jcStatements.toList());

            JCTree.JCVariableDecl param = treeMaker.VarDef(
                    treeMaker.Modifiers(Flags.PARAMETER),
                    field.name,
                    field.vartype,
                    null
            );

            return treeMaker.MethodDef(
                    treeMaker.Modifiers(Flags.PUBLIC),
                    fieldName,
                    treeMaker.Ident(names.fromString(BUILDER_CLASS_NAME_SUFFIX)),
                    List.nil(),
                    List.of(param),
                    List.nil(),
                    jcBlock,
                    null
            );
        }

        private JCTree.JCMethodDecl createBuildMethod(
                JCTree.JCClassDecl jcClass, List<JCTree.JCVariableDecl> fields) {
            JCTree.JCIdent targetId = treeMaker.Ident(jcClass.name);

            ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();

            Name instanceName = names.fromString("instance");
            JCTree.JCNewClass newClass = treeMaker.NewClass(
                    null, List.nil(), targetId, List.nil(), null);
            JCTree.JCVariableDecl instanceDecl = treeMaker.VarDef(
                    treeMaker.Modifiers(0), instanceName, targetId, newClass);
            statements.append(instanceDecl);

            Name thisIdName = names.fromString("this");
            JCTree.JCIdent thisId = treeMaker.Ident(thisIdName);
            JCTree.JCIdent instanceId = treeMaker.Ident(instanceName);
            for (JCTree.JCVariableDecl field : fields) {
                Name fieldName = names.fromString(field.name.toString());
                JCTree.JCStatement statement = treeMaker.Exec(treeMaker.Assign(
                        treeMaker.Select(instanceId, fieldName), treeMaker.Select(thisId, fieldName)));
                statements.append(statement);
            }

            JCTree.JCStatement returnStatement = treeMaker.Return(instanceId);
            statements.append(returnStatement);

            JCTree.JCBlock jcBlock = treeMaker.Block(0, statements.toList());

            return treeMaker.MethodDef(
                    treeMaker.Modifiers(Flags.PUBLIC),
                    names.fromString("build"),
                    targetId,
                    List.nil(),
                    List.nil(),
                    List.nil(),
                    jcBlock,
                    null
            );
        }

    }
}
