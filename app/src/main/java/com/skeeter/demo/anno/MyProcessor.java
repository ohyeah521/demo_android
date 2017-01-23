//package com.skeeter.demo.anno;
//
//import java.lang.annotation.Annotation;
//import java.util.LinkedHashSet;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;
//
//import javax.annotation.processing.AbstractProcessor;
//import javax.annotation.processing.Filer;
//import javax.annotation.processing.ProcessingEnvironment;
//import javax.annotation.processing.RoundEnvironment;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.ElementKind;
//import javax.lang.model.element.TypeElement;
//import javax.lang.model.type.DeclaredType;
//import javax.lang.model.type.TypeMirror;
//import javax.lang.model.util.Elements;
//import javax.lang.model.util.Types;
//
///**
// * @author michael created on 2016/12/2.
// */
//public class MyProcessor extends AbstractProcessor {
//
//    private Elements elementUtils;
//    private Types typeUtils;
//    private Filer filer;
//
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        Set<String> annotationTypes = new LinkedHashSet<>(1);
//        annotationTypes.add(TestAnno.class.getCanonicalName());
//        return annotationTypes;
//    }
//
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnvironment) {
//        super.init(processingEnvironment);
//
//        elementUtils = processingEnvironment.getElementUtils();
//        typeUtils = processingEnvironment.getTypeUtils();
//        filer = processingEnvironment.getFiler();
//
//        Map<String, String> options = processingEnvironment.getOptions();
//        for (String key : options.keySet()) {
//            System.out.println(key + " ===> " + options.get(key));
//        }
//    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        //  先用一个map缓存annotation的处理结果，Key是要生成对应Processor的类的类名
//        Map<String, BindingClass> bindingClassMap = new TreeMap<>();
//
//        for (TypeElement typeElement : set) {
//            try {
//                Class annoClazz = Class.forName(typeElement.asType().toString());
//                System.out.println("processing " + annoClazz.getCanonicalName());
//
//                for (Element element : roundEnvironment.getElementsAnnotatedWith(typeElement)) {
//                    System.out.println("elements: (use typeElement) " + element.toString());
//                    if (!isValid(element, annoClazz)){
//                        continue;
//                    }
//
//
//                }
//
//                for (Object ele : roundEnvironment.getElementsAnnotatedWith(annoClazz)) {
//                    System.out.println("elements: (use clazz) " + ele.toString());
//
//
//                }
//
//
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//
//        return false;
//    }
//
//    /**
//     * 判断是否typeElement是否允许被annotationClz正确修饰
//     */
//    public boolean isValid(Element element, Class<? extends Annotation> anno) {
//        if (anno.equals(TestAnno.class)) {
//            return element.getKind().equals(ElementKind.FIELD) &&
//                    isSubtypeOfType(element.asType(), int.class.getName());
//        }
//        return false;
//    }
//
//
//    /**
//     * 检查typeMirror是否是otherType的子类
//     *
//     * @param typeMirror
//     * @param otherType
//     * @return
//     */
//    public static boolean isSubtypeOfType(TypeMirror typeMirror, String otherType) {
//        if (otherType.equals(typeMirror.toString())) {
//            return true;
//        }
//        if (!(typeMirror instanceof DeclaredType)) {
//            return false;
//        }
//        DeclaredType declaredType = (DeclaredType) typeMirror;
//        Element element = declaredType.asElement();
//        if (!(element instanceof TypeElement)) {
//            return false;
//        }
//        TypeElement typeElement = (TypeElement) element;
//        TypeMirror superType = typeElement.getSuperclass();
//        if (isSubtypeOfType(superType, otherType)) {
//            return true;
//        }
//        for (TypeMirror interfaceType : typeElement.getInterfaces()) {
//            if (isSubtypeOfType(interfaceType, otherType)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//}
