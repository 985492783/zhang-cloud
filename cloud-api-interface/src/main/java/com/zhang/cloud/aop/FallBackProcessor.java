package com.zhang.cloud.aop;

import com.google.auto.service.AutoService;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Symbol;
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
 * @date 2021/12/12 16:46
 */
@SupportedAnnotationTypes("com.zhang.cloud.aop.FallBack")
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class FallBackProcessor extends AbstractProcessor {
    private JavacTrees trees;
    private TreeMaker treeMaker;
    private Names names;
    private Messager messager;
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
        roundEnv.getElementsAnnotatedWith(FallBack.class).stream()
                .map(element ->trees.getTree(element))
                .forEach(tree->tree.accept(new TreeTranslator(){
                    @Override
                    public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl){
                        messager.printMessage(Diagnostic.Kind.NOTE,"@VOID "+jcMethodDecl.restype.toString());
                        if(jcMethodDecl.restype.toString().equals("void")){
                            return;
                        }
                        JCTree.JCVariableDecl ex=treeMaker.VarDef(treeMaker.Modifiers(0), names.fromString("e"), memberAccess("java.lang.Exception"),null);
                        JCTree.JCExpressionStatement app=treeMaker.Exec(treeMaker.Apply(List.nil(),treeMaker.Select(treeMaker.Ident(names.fromString("e")),names.fromString("printStackTrace")),List.nil()));
                        JCTree.JCCatch cat=treeMaker.Catch(ex,treeMaker.Block(0, List.of(app,treeMaker.Return(treeMaker.NewClass(null,List.nil(),jcMethodDecl.restype,List.nil(),null)))));
                        JCTree.JCTry jcTry=treeMaker.Try(treeMaker.Block(0,jcMethodDecl.body.stats),List.of(cat),null);
                        jcMethodDecl.body.stats=List.of(jcTry);
                        messager.printMessage(Diagnostic.Kind.NOTE,"@FallBack "+jcMethodDecl.name.toString());
                        super.visitMethodDef(jcMethodDecl);
                    }
                }));
        return true;
    }

    private JCTree.JCExpression memberAccess(String components) {
        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(names.fromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, names.fromString(componentArray[i]));
        }
        return expr;
    }

}
