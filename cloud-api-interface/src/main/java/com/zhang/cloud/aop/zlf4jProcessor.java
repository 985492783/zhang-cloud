package com.zhang.cloud.aop;

import com.google.auto.service.AutoService;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
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
 * @date 2021/12/12 21:37
 */
@SupportedAnnotationTypes("com.zhang.cloud.aop.zlf4j")
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class zlf4jProcessor extends AbstractProcessor {
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
        roundEnv.getElementsAnnotatedWith(zlf4j.class).stream()
                .map(element -> trees.getPath(element))
                .filter(element->element.getCompilationUnit() instanceof JCTree.JCCompilationUnit&&element.getLeaf()instanceof JCTree)
                .forEach(tree ->{
                    JCTree.JCCompilationUnit jcCompilationUnit = (JCTree.JCCompilationUnit) tree.getCompilationUnit();
                    JCTree.JCIdent ident = treeMaker.Ident(names.fromString("org.apache.logging.log4j"));
                    JCTree.JCImport jcImport = treeMaker.Import(treeMaker.Select(ident,names.fromString("*")), false);
                    jcCompilationUnit.defs=jcCompilationUnit.defs.prepend(jcImport);
                });
        roundEnv.getElementsAnnotatedWith(zlf4j.class).stream()
                .map(element ->trees.getTree(element))
                .forEach(tree ->tree.accept(new TreeTranslator(){
                    @Override
                    public void visitClassDef(JCTree.JCClassDecl jcClassDecl){
                        JCTree.JCVariableDecl jcVariableDecl=treeMaker.VarDef(treeMaker.Modifiers(Flags.PRIVATE | Flags.STATIC),names.fromString("log"),memberAccess("org.apache.logging.log4j.Logger"),treeMaker.Apply(List.nil(),treeMaker.Select(treeMaker.Ident(names.fromString("LogManager")), names.fromString("getLogger")),List.nil()));
                        jcClassDecl.defs=jcClassDecl.defs.prepend(jcVariableDecl);
                        jcClassDecl.defs.stream()
                                .filter(element -> element.getKind().equals(Tree.Kind.METHOD))
                                .map(methodTree -> (JCTree.JCMethodDecl) methodTree)
                                .filter(methodTree->!methodTree.name.toString().equals("<init>"))
                                .forEach(methodTree ->{
                                    messager.printMessage(Diagnostic.Kind.NOTE,"@Method "+methodTree.name.toString());
                                    JCTree.JCExpressionStatement printLiteral = treeMaker.Exec(treeMaker.Apply(
                                                    List.of(memberAccess("java.lang.String"),memberAccess("java.lang.String")),//参数类型
                                                    treeMaker.Select(treeMaker.Ident(names.fromString("log")),names.fromString("info")),
                                                    List.of(treeMaker.Literal("***方法：{}被调用！"),treeMaker.Literal(methodTree.name.toString()))//参数集合
                                            ));
                                    methodTree.body.stats=methodTree.body.stats.prepend(printLiteral);
                                });
                        super.visitClassDef(jcClassDecl);
                    }
                }));
        return true;
    }
    private JCTree.JCAnnotation createMapper(){
        return treeMaker.Annotation(
                treeMaker.Ident(names.fromString("Slf4j")),
                List.nil());
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
