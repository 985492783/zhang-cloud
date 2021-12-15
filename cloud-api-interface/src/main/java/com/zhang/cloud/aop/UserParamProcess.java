package com.zhang.cloud.aop;

import com.google.auto.service.AutoService;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @author 98549
 * @date 2021/12/9 21:45
 */
@SupportedAnnotationTypes("com.zhang.cloud.aop.UserParam")
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class UserParamProcess extends AbstractProcessor {
    private JavacTrees trees;
    private TreeMaker treeMaker;
    private Names names;
    Messager messager;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(UserParam.class).stream()
                .map(element -> trees.getPath(element))
                .filter(element->element.getCompilationUnit() instanceof JCTree.JCCompilationUnit&&element.getLeaf()instanceof JCTree)
                .forEach(tree ->{
                    JCTree.JCCompilationUnit jcCompilationUnit = (JCTree.JCCompilationUnit) tree.getCompilationUnit();
                    JCTree.JCIdent ident = treeMaker.Ident(names.fromString("org.apache.ibatis.annotations"));
                    JCTree.JCImport jcImport = treeMaker.Import(treeMaker.Select(ident,names.fromString("*")), false);
                    jcCompilationUnit.defs=jcCompilationUnit.defs.prepend(jcImport);
                });
        roundEnv.getElementsAnnotatedWith(UserParam.class).stream()
                .map(element ->trees.getTree(element))
                .forEach(tree->tree.accept(new TreeTranslator(){
                    @Override
                    public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                        jcClassDecl.defs.stream()
                                .filter(element -> element.getKind().equals(Tree.Kind.METHOD))
                                .map(methodTree -> (JCTree.JCMethodDecl) methodTree)
                                .forEach(methodTree -> {
                                    methodTree.getModifiers().annotations=methodTree.getModifiers().annotations.prepend(createAnnotation(methodTree));
                                    methodTree.getParameters().forEach(parameter -> {
                                        JCTree.JCAnnotation paramAnnotation = createParamAnnotation(parameter);
                                        parameter.getModifiers().annotations=parameter.getModifiers().annotations.prepend(paramAnnotation);
                                    });
                                });
                        jcClassDecl.getModifiers().annotations=jcClassDecl.getModifiers().annotations.prepend(createMapper());
                        super.visitClassDef(jcClassDecl);
                    }
                }));
        return true;
    }
    private JCTree.JCAnnotation createMapper(){
        return treeMaker.Annotation(
                treeMaker.Ident(names.fromString("Mapper")),
                List.nil());
    }
    private JCTree.JCAnnotation createAnnotation(JCTree.JCMethodDecl jcMethodDecl){
        String name=jcMethodDecl.name.toString();
        String var1=null,var2=null;
        int index1=name.indexOf("find");
        int index2=name.indexOf("By");
        if(index1!=-1 && index2!=-1){
            var1=name.substring(4,5).toLowerCase()+name.substring(5,index2);
            var2=name.substring(index2+2,index2+3).toLowerCase()+name.substring(index2+3);
        }else{
            return null;
        }
        String sql="SELECT * FROM "+var1+" WHERE "+var2+"=#{"+var2+"}";
        return treeMaker.Annotation(
                treeMaker.Ident(names.fromString("Select")),
                List.of(treeMaker.Assign(treeMaker.Ident(names.fromString("value")),treeMaker.Literal(sql))));
    }
    private JCTree.JCAnnotation createParamAnnotation(JCTree.JCVariableDecl parameter) {
        return treeMaker.Annotation(
                treeMaker.Ident(names.fromString("Param")),
                List.of(treeMaker.Assign(treeMaker.Ident(names.fromString("value")),treeMaker.Literal(parameter.name.toString()))));
    }
}
