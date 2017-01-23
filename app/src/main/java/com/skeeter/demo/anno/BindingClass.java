//package com.skeeter.demo.anno;
//
//import android.content.Intent;
//
//import java.lang.annotation.Annotation;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Locale;
//
//import javax.lang.model.element.Element;
//
//import me.chunyu.g7anno.G7AnnoUtils;
//import me.chunyu.g7anno.annotation.BroadcastResponder;
//import me.chunyu.g7anno.annotation.ClickResponder;
//import me.chunyu.g7anno.annotation.ContentView;
//import me.chunyu.g7anno.annotation.FullScreen;
//import me.chunyu.g7anno.annotation.IntentArgs;
//import me.chunyu.g7anno.annotation.ViewBinding;
//import me.chunyu.g7anno.processor.ActivityProcessor;
//import me.chunyu.g7anno.processor.ContextProcessor;
//import me.chunyu.g7anno.processor.FragmentProcessor;
//import me.chunyu.g7anno.processor.GeneralProcessor;
//
///**
// * Created by eden on 4/29/15.
// */
//public class BindingClass {
//
//    private int mClassType;
//
//    private Element mEnclosingPackage;
//
//    private Element mEnclosingClass;
//
//    private String mOutClassName;
//
//    private BindingClass mParentBindingClass;
//
//    private HashMap<String, List<Annotator>> mElementMap = new HashMap<>();
//
//    public BindingClass(Element enclosingPackage, Element enclosingClass,
//            String outClassName) {
//        mEnclosingPackage = enclosingPackage;
//        mEnclosingClass = enclosingClass;
//        mOutClassName = outClassName;
//        mClassType = getEnclosingClassType();
//    }
//
//    public String getPackageName() {
//        return mEnclosingPackage.asType().toString()
//                .replaceAll("<\\w+>", "");    //  如果有模板类，会携带<T>字样，把这些都去掉
//    }
//
//    public String getOutClassName() {
//        return mOutClassName;
//    }
//
//    public Element getEnclosingPackage() {
//        return mEnclosingPackage;
//    }
//
//    public Element getEnclosingClass() {
//        return mEnclosingClass;
//    }
//
//    public BindingClass getParentBindingClass() {
//        return mParentBindingClass;
//    }
//
//    public void setParentBindingClass(BindingClass parentBindingClass) {
//        /**
//         * TODO: 在跨模块的继承时，例如A集成B，但处在不同的模块中，AnnotationProcessor无法跨模块执行，
//         * 因此无法正确形成继承关系，Processor的继承关系就会失效。
//         * 暂时先不处理继承关系，看以后有没有好的办法可以解决。
//         */
////        mParentBindingClass = parentBindingClass;
//    }
//
//    public String getEnclosingClassFullName() {
//        return mEnclosingClass.asType().toString()
//                .replaceAll("<\\w+>", "");    //  如果有模板类，会携带<T>字样，把这些都去掉
//    }
//
//    /**
//     * 获取所属类的类型，类型见{@link G7AnnoUtils#CLASS_TYPE_ACTIVITY}, {@link G7AnnoUtils#CLASS_TYPE_FRAGMENT},
//     * {@link G7AnnoUtils#CLASS_TYPE_GENERAL}
//     *
//     * @return 返回的类型
//     */
//    private int getEnclosingClassType() {
//        if (G7AnnoUtils
//                .isSubtypeOfType(mEnclosingClass.asType(), "android.app.Activity")) {
//            return G7AnnoUtils.CLASS_TYPE_ACTIVITY;
//        } else if (G7AnnoUtils
//                .isSubtypeOfType(mEnclosingClass.asType(), "android.support.v4.app.Fragment")) {
//            return G7AnnoUtils.CLASS_TYPE_FRAGMENT_V4;
//        } else if (G7AnnoUtils.isSubtypeOfType(mEnclosingClass.asType(), "android.app.Fragment")) {
//            return G7AnnoUtils.CLASS_TYPE_FRAGMENT;
//        } else if (G7AnnoUtils
//                .isSubtypeOfType(mEnclosingClass.asType(), "android.content.Context")) {
//            return G7AnnoUtils.CLASS_TYPE_CONTEXT;
//        } else {
//            return G7AnnoUtils.CLASS_TYPE_GENERAL;
//        }
//    }
//
//    /**
//     * 加入类自身的annotation
//     *
//     * @param annotation 所使用的Annotation
//     */
//    public void addAttribute(Annotation annotation) {
//        String annoType = annotation.annotationType().getCanonicalName();
////        System.out.println("add self with " + annoType + " to " + getPackageName() + "." +
////                mOutClassName);
//        List<Annotator> list = mElementMap.get(annoType);
//        if (list == null) {
//            list = new LinkedList<>();
//        }
////        System.out.println("add attribute " + annoType);
//        list.add(new Annotator(mEnclosingClass, annotation));
//        mElementMap.put(annoType, list);
//    }
//
//    /**
//     * 加入类的成员或者方法的Annotation
//     *
//     * @param element    被修饰的Element
//     * @param annotation 所使用的Annotation
//     */
//    public void addAttribute(Element element, Annotation annotation) {
//        String annoType = annotation.annotationType().getCanonicalName();
////        System.out.println(
////                "add " + element.getSimpleName() + " with " + annoType + " to " + getPackageName() +
////                        "." + mOutClassName);
//        List<Annotator> list = mElementMap.get(annoType);
//        if (list == null) {
//            list = new LinkedList<>();
//        }
////        System.out.println("add attribute " + element.asType().toString() + ", " +
////                element.getSimpleName().toString());
//        list.add(new Annotator(element, annotation));
//        mElementMap.put(annoType, list);
//    }
//
//    /**
//     * 生成代码的函数
//     *
//     * @return 生成的Processor的代码段
//     */
//    public String brewSource() {
//        switch (mClassType) {
//            case G7AnnoUtils.CLASS_TYPE_ACTIVITY:
//                return brewActivitySource();
//            case G7AnnoUtils.CLASS_TYPE_FRAGMENT:
//                return brewFragmentSource(false);
//            case G7AnnoUtils.CLASS_TYPE_FRAGMENT_V4:
//                return brewFragmentSource(true);
//            case G7AnnoUtils.CLASS_TYPE_CONTEXT:
//                return brewContextSource();
//            case G7AnnoUtils.CLASS_TYPE_GENERAL:
//                return brewGeneralSource();
//            default:
//                return "";
//        }
//    }
//
//    /**
//     * 生成{@link ActivityProcessor}的派生类的代码
//     *
//     * @return 为Activity生成的Processor的代码段
//     */
//    private String brewActivitySource() {
//        System.out.println("packageName: " + getPackageName() + ", " + mOutClassName);
//        StringBuilder builder = new StringBuilder();
//        builder.append("/***** Auto-generated by G7Anno. DO NOT modify! *****/\r\n\r\n");
//        builder.append("package ").append(getPackageName()).append(";\r\n\r\n");
//        builder.append("public class ").append(G7AnnoUtils.getProcessorSimpleName(mOutClassName))
//                .append("<T extends ").append(getEnclosingClassFullName());
//
//        if (mParentBindingClass == null) {
//            builder.append("> extends me.chunyu.g7anno.processor.ActivityProcessor<T>")
//                    .append(" {\r\n\r\n");
//        } else {
//            builder.append("> extends ").append(mParentBindingClass.getPackageName())
//                    .append(".")
//                    .append(G7AnnoUtils
//                            .getProcessorSimpleName(mParentBindingClass.getOutClassName()))
//                    .append("<T>").append(" {\r\n\r\n");
//        }
//
//        /**
//         * 给放到{@link ActivityProcessor#bindViews(G7Activity)} 的方法里进行处理的annotation单独
//         * 建一个StringBuilder，这些annotation和ContentView之类的不一样，
//         * ContentView只会出现一次，并且对应Processor中一个完整的函数，修饰域的annotation会出现多次，
//         * 每个只对应一条语句，并且只有在全部处理完毕之后，才会生成一个函数，所以要单独一个StringBuilder
//         * 来缓存这些语句。
//         */
//        StringBuilder bindingBuilder = new StringBuilder();
//
//        /**
//         * 给放到{@link ActivityProcessor#getBroadcastIntentFilter(G7Activity)} 和
//         * {@link ActivityProcessor#onReceiveBroadcast(G7Activity, Intent)} 两个方法里进行处理的
//         * {@link me.chunyu.g7anno.annotation.BroadcastResponder} annotation也要单独建两个StringBuilder，
//         * 因为要生成两个方法。
//         */
//        StringBuilder intentFilterBuilder = new StringBuilder();
//        StringBuilder receiverBuilder = new StringBuilder();
//
//        /**
//         * 给放到{@link ActivityProcessor#parseExtras(G7Activity, android.os.Bundle)} 方法里进行处理的语句建一个StringBuilder。
//         */
//        StringBuilder intentArgsBuilder = new StringBuilder();
//
//        //  第一遍循环，先处理需要单独写函数的annotation
//        for (String key : mElementMap.keySet()) {
//            List<Annotator> annotatorList = mElementMap.get(key);
//            for (Annotator annotator : annotatorList) {
//                System.out
//                        .println(annotator.getElementType().toString() + ", " +
//                                annotator.getSimpleName() + ", " +
//                                annotator.getAnnotation().annotationType().toString());
//                if (key.equals(ContentView.class.getCanonicalName())) {
//                    //  处理ContentView
//                    builder.append(CodeGenerater.generateContentViewMethod(annotator))
//                            .append("\r\n");
//                } else if (key.equals(FullScreen.class.getCanonicalName())) {
//                    //  处理FullScreen
//                    builder.append(CodeGenerater.generateFullScreenMethod(annotator))
//                            .append("\r\n");
//                } else if (key.equals(ViewBinding.class.getCanonicalName())) {
//                    //  处理ViewBinding
//                    bindingBuilder.append(CodeGenerater
//                            .generateViewBindingCommand(annotator, "instance", "instance",
//                                    "instance"))
//                            .append("\r\n");
//                } else if (key.equals(ClickResponder.class.getCanonicalName())) {
//                    //  处理ClickResponder
//                    bindingBuilder
//                            .append(CodeGenerater
//                                    .generateClickResponderCommand(annotator, "instance",
//                                            "instance", "instance"))
//                            .append("\r\n");
//                } else if (key.equals(BroadcastResponder.class.getCanonicalName())) {
//                    //  处理BroadcastResponder
//                    if (intentFilterBuilder.length() > 0) {
//                        intentFilterBuilder.append(",");
//                    }
//                    intentFilterBuilder
//                            .append(CodeGenerater.generateIntentFilterCommand(annotator));
//                    receiverBuilder
//                            .append(CodeGenerater.generateReceiveBroadcastCommand(annotator, "t"));
//                } else if (key.equals(IntentArgs.class.getCanonicalName())) {
//                    //  处理IntentArgs
//                    intentArgsBuilder.append(CodeGenerater
//                            .generateIntentArgsCommand(annotator, "t", "bundle"))
//                            .append("\r\n");
//                }
//            }
//        }
//
//        String bindingStr = bindingBuilder.toString();
//        if (bindingStr.length() > 0) {
//            bindingStr = String.format(Locale.getDefault(),
//                    "@Override\r\nprotected void bindViewsInternal(T t) {\r\n" +
//                            "\tandroid.view.View tmpview = null;\n" +
//                            "\tfinal %s instance = t;\r\n%s\r\n}", getEnclosingClassFullName(),
//                    bindingStr);
//            builder.append(bindingStr);
//        }
//
//        String filterStr = intentFilterBuilder.toString();
//        if (filterStr.length() > 0) {
////            filterStr = String.format(Locale.getDefault(),
////                    "\r\n@Override\r\nprotected android.content.IntentFilter getBroadcastIntentFilter(T t) {\r\n" +
////                            "\tandroid.content.IntentFilter filter = super.getBroadcastIntentFilter(t);\r\n" +
////                            "\tif (filter == null) {\r\n\tfilter = new android.content.IntentFilter();\r\n\t}\r\n" +
////                            "%s\r\n\r\nreturn filter;\r\n}\r\n\r\n", filterStr);
//            filterStr = String.format(Locale.getDefault(),
//                    "\r\n@Override\r\nprotected java.lang.String[] getBroadcastActions(T t) {\r\n" +
//                            "return new java.lang.String[] {%s};\r\n}\r\n\r\n", filterStr);
//            builder.append(filterStr);
//        }
//
//        String receiverStr = receiverBuilder.toString();
//        if (receiverStr.length() > 0) {
//            receiverStr = String.format(Locale.getDefault(),
//                    "\r\n@Override\r\npublic void onReceiveBroadcastInternal(T t, android.content.Intent intent) {\r\n" +
//                            "\tString action = intent.getAction();\r\n" +
//                            "\tswitch (action) {\r\n%s\r\n}\r\n}\r\n\r\n", receiverStr);
//            builder.append(receiverStr);
//        }
//
//        String intentArgsStr = intentArgsBuilder.toString();
//        if (intentArgsStr.length() > 0) {
//            intentArgsStr = String.format(Locale.getDefault(),
//                    "\r\n@Override\r\nprotected void parseExtrasInternal(T t, android.os.Bundle bundle) {\r\n" +
//                            "\tif (bundle == null) return;\r\n" +
//                            "\t%s\r\n}\r\n\r\n", intentArgsStr);
//            builder.append(intentArgsStr);
//        }
//
//        builder.append("}");
//
//        return builder.toString();
//    }
//
//    /**
//     * 生成{@link FragmentProcessor}的派生类的代码
//     *
//     * @param isSupport 是否是Support库的Fragment
//     * @return 为Fragment生成的Processor的代码段
//     */
//    private String brewFragmentSource(boolean isSupport) {
//        System.out.println("packageName: " + getPackageName() + ", " + mOutClassName);
//        StringBuilder builder = new StringBuilder();
//        builder.append("/***** Auto-generated by G7Anno. DO NOT modify! *****/\r\n\r\n");
//        builder.append("package ").append(getPackageName()).append(";\r\n\r\n");
//        builder.append("public class ").append(G7AnnoUtils.getProcessorSimpleName(mOutClassName))
//                .append("<T extends ").append(getEnclosingClassFullName());
//
//        if (mParentBindingClass == null) {
//            if (isSupport) {
//                builder.append("> extends me.chunyu.g7anno.processor.V4FragmentProcessor<T>")
//                        .append(" {\r\n\r\n");
//            } else {
//                builder.append("> extends me.chunyu.g7anno.processor.FragmentProcessor<T>")
//                        .append(" {\r\n\r\n");
//            }
//        } else {
//            builder.append("> extends ").append(mParentBindingClass.getPackageName())
//                    .append(".")
//                    .append(G7AnnoUtils
//                            .getProcessorSimpleName(mParentBindingClass.getOutClassName()))
//                    .append("<T>").append(" {\r\n\r\n");
//        }
//
//        /**
//         * 给放到{@link FragmentProcessor#bindViews(G7Activity)} 的方法里进行处理的annotation单独
//         * 建一个StringBuilder，这些annotation和ContentView之类的不一样，
//         * ContentView只会出现一次，并且对应Processor中一个完整的函数，修饰域的annotation会出现多次，
//         * 每个只对应一条语句，并且只有在全部处理完毕之后，才会生成一个函数，所以要单独一个StringBuilder
//         * 来缓存这些语句。
//         */
//        StringBuilder bindingBuilder = new StringBuilder();
//
//        /**
//         * 给放到{@link FragmentProcessor#getBroadcastIntentFilter(G7Activity)} 和
//         * {@link ActivityProcessor#onReceiveBroadcast(G7Activity, Intent)} 两个方法里进行处理的
//         * {@link me.chunyu.g7anno.annotation.BroadcastResponder} annotation也要单独建两个StringBuilder，
//         * 因为要生成两个方法。
//         */
//        StringBuilder intentFilterBuilder = new StringBuilder();
//        StringBuilder receiverBuilder = new StringBuilder();
//
//        /**
//         * 给放到{@link FragmentProcessor#parseExtras(G7Activity, android.os.Bundle)} 方法里进行处理的语句建一个StringBuilder。
//         */
//        StringBuilder intentArgsBuilder = new StringBuilder();
//
//        //  第一遍循环，先处理需要单独写函数的annotation
//        for (String key : mElementMap.keySet()) {
//            List<Annotator> annotatorList = mElementMap.get(key);
//            for (Annotator annotator : annotatorList) {
//                System.out
//                        .println(annotator.getElementType().toString() + ", " +
//                                annotator.getSimpleName() + ", " +
//                                annotator.getAnnotation().annotationType().toString());
//                if (key.equals(ContentView.class.getCanonicalName())) {
//                    //  处理ContentView
//                    builder.append(CodeGenerater.generateContentViewMethod(annotator))
//                            .append("\r\n");
//                } else if (key.equals(FullScreen.class.getCanonicalName())) {
//                    //  处理FullScreen
//                    builder.append(CodeGenerater.generateFullScreenMethod(annotator))
//                            .append("\r\n");
//                } else if (key.equals(ViewBinding.class.getCanonicalName())) {
//                    //  处理ViewBinding
//                    bindingBuilder.append(CodeGenerater
//                            .generateViewBindingCommand(annotator, "t", "v", "t.getActivity()"))
//                            .append("\r\n");
//                } else if (key.equals(ClickResponder.class.getCanonicalName())) {
//                    //  处理ClickResponder
//                    bindingBuilder
//                            .append(CodeGenerater
//                                    .generateClickResponderCommand(annotator, "instance", "v",
//                                            "instance.getActivity()"))
//                            .append("\r\n");
//                } else if (key.equals(BroadcastResponder.class.getCanonicalName())) {
//                    //  处理BroadcastResponder
//                    if (intentFilterBuilder.length() > 0) {
//                        intentFilterBuilder.append(",");
//                    }
//                    intentFilterBuilder
//                            .append(CodeGenerater.generateIntentFilterCommand(annotator));
//                    receiverBuilder
//                            .append(CodeGenerater
//                                    .generateReceiveBroadcastCommand(annotator, "t.getActivity()"));
//                } else if (key.equals(IntentArgs.class.getCanonicalName())) {
//                    //  处理IntentArgs
//                    intentArgsBuilder.append(CodeGenerater
//                            .generateIntentArgsCommand(annotator, "t", "bundle"))
//                            .append("\r\n");
//                }
//            }
//        }
//
//        String bindingStr = bindingBuilder.toString();
//        if (bindingStr.length() > 0) {
//            if (isSupport) {
//                bindingStr = String.format(Locale.getDefault(),
//                        "@Override\r\nprotected void bindViewsInternal(T t, android.view.View v) {\r\n" +
//                                "\tandroid.view.View tmpview = null;\n" +
//                                "\tfinal %s instance = t;\r\n%s\r\n}", getEnclosingClassFullName(),
//                        bindingStr);
//            } else {
//                bindingStr = String.format(Locale.getDefault(),
//                        "@Override\r\nprotected void bindViewsInternal(T t, android.view.View v) {\r\n" +
//                                "\tandroid.view.View tmpview = null;\n" +
//                                "\tfinal %s instance = t;\r\n%s\r\n}", getEnclosingClassFullName(),
//                        bindingStr);
//            }
//            builder.append(bindingStr);
//        }
//
//        String filterStr = intentFilterBuilder.toString();
//        if (filterStr.length() > 0) {
////            filterStr = String.format(Locale.getDefault(),
////                    "\r\n@Override\r\nprotected android.content.IntentFilter getBroadcastIntentFilter(T t) {\r\n" +
////                            "\tandroid.content.IntentFilter filter = super.getBroadcastIntentFilter(t);\r\n" +
////                            "\tif (filter == null) {\r\n\tfilter = new android.content.IntentFilter();\r\n\t}\r\n" +
////                            "%s\r\n\r\nreturn filter;\r\n}\r\n\r\n", filterStr);
//            filterStr = String.format(Locale.getDefault(),
//                    "\r\n@Override\r\nprotected java.lang.String[] getBroadcastActions(T t) {\r\n" +
//                            "return new java.lang.String[] {%s};\r\n}\r\n\r\n", filterStr);
//            builder.append(filterStr);
//        }
//
//        String receiverStr = receiverBuilder.toString();
//        if (receiverStr.length() > 0) {
//            receiverStr = String.format(Locale.getDefault(),
//                    "\r\n@Override\r\npublic void onReceiveBroadcastInternal(T t, android.content.Intent intent) {\r\n" +
//                            "\tString action = intent.getAction();\r\n" +
//                            "\tswitch (action) {\r\n%s\r\n}\r\n}\r\n\r\n", receiverStr);
//            builder.append(receiverStr);
//        }
//
//        String intentArgsStr = intentArgsBuilder.toString();
//        if (intentArgsStr.length() > 0) {
//            intentArgsStr = String.format(Locale.getDefault(),
//                    "\r\n@Override\r\nprotected void parseExtrasInternal(T t, android.os.Bundle bundle) {\r\n" +
//                            "\tif (bundle == null) return;\r\n" +
//                            "\t%s\r\n}\r\n\r\n", intentArgsStr);
//            builder.append(intentArgsStr);
//        }
//
//        builder.append("}");
//
//        return builder.toString();
//    }
//
//    /**
//     * 生成{@link GeneralProcessor}的派生类的代码
//     *
//     * @return 为通用类生成的Processor的代码段
//     */
//    private String brewGeneralSource() {
//        System.out.println("packageName: " + getPackageName() + ", " + mOutClassName);
//        StringBuilder builder = new StringBuilder();
//        builder.append("/***** Auto-generated by G7Anno. DO NOT modify! *****/\r\n\r\n");
//        builder.append("package ").append(getPackageName()).append(";\r\n\r\n");
//        builder.append("public class ").append(G7AnnoUtils.getProcessorSimpleName(mOutClassName))
//                .append("<T extends ").append(getEnclosingClassFullName());
//
//        if (mParentBindingClass == null) {
//            builder.append("> extends me.chunyu.g7anno.processor.GeneralProcessor<T>")
//                    .append(" {\r\n\r\n");
//        } else {
//            builder.append("> extends ").append(mParentBindingClass.getPackageName())
//                    .append(".")
//                    .append(G7AnnoUtils
//                            .getProcessorSimpleName(mParentBindingClass.getOutClassName()))
//                    .append("<T>").append(" {\r\n\r\n");
//        }
//
//        /**
//         * 给放到{@link FragmentProcessor#bindViews(G7Activity)} 的方法里进行处理的annotation单独
//         * 建一个StringBuilder，这些annotation和ContentView之类的不一样，
//         * ContentView只会出现一次，并且对应Processor中一个完整的函数，修饰域的annotation会出现多次，
//         * 每个只对应一条语句，并且只有在全部处理完毕之后，才会生成一个函数，所以要单独一个StringBuilder
//         * 来缓存这些语句。
//         */
//        StringBuilder bindingBuilder = new StringBuilder();
//
//        /**
//         * 给放到{@link ActivityProcessor#parseExtras(G7Activity)} 方法里进行处理的语句建一个StringBuilder。
//         */
//        StringBuilder intentArgsBuilder = new StringBuilder();
//
//        //  第一遍循环，先处理需要单独写函数的annotation
//        for (String key : mElementMap.keySet()) {
//            List<Annotator> annotatorList = mElementMap.get(key);
//            for (Annotator annotator : annotatorList) {
//                System.out
//                        .println(annotator.getElementType().toString() + ", " +
//                                annotator.getSimpleName() + ", " +
//                                annotator.getAnnotation().annotationType().toString());
//                if (key.equals(ViewBinding.class.getCanonicalName())) {
//                    //  处理ViewBinding
//                    bindingBuilder.append(CodeGenerater
//                            .generateViewBindingCommand(annotator, "instance", "v",
//                                    "v.getContext()"))
//                            .append("\r\n");
//                } else if (key.equals(IntentArgs.class.getCanonicalName())) {
//                    //  处理IntentArgs
//                    intentArgsBuilder.append(CodeGenerater
//                            .generateIntentArgsCommand(annotator, "t", "bundle"))
//                            .append("\r\n");
//                } else if (key.equals(ClickResponder.class.getCanonicalName())) {
//                    //  处理ClickResponder
//                    bindingBuilder
//                            .append(CodeGenerater
//                                    .generateClickResponderCommand(annotator, "instance",
//                                            "v", "v.getContext()"))
//                            .append("\r\n");
//                }
//            }
//        }
//
//        String bindingStr = bindingBuilder.toString();
//        if (bindingStr.length() > 0) {
//            bindingStr = String.format(Locale.getDefault(),
//                    "@Override\r\nprotected void bindViewsInternal(T t, android.view.View v) {\r\n" +
//                            "\tandroid.view.View tmpview = null;\n" +
//                            "\tfinal %s instance = t;\r\n%s\r\n}", getEnclosingClassFullName(),
//                    bindingStr);
//            builder.append(bindingStr);
//        }
//
//        String intentArgsStr = intentArgsBuilder.toString();
//        if (intentArgsStr.length() > 0) {
//            intentArgsStr = String.format(Locale.getDefault(),
//                    "\r\n@Override\r\nprotected void parseExtrasInternal(T t, android.os.Bundle bundle) {\r\n" +
//                            "\tif (bundle == null) return;\r\n" +
//                            "\t%s\r\n}\r\n\r\n", intentArgsStr);
//            builder.append(intentArgsStr);
//        }
//
//        builder.append("}");
//
//        return builder.toString();
//    }
//
//    /**
//     * 生成{@link ContextProcessor}的派生类的代码
//     *
//     * @return 为Context派生类生成的Processor的代码段
//     */
//    private String brewContextSource() {
//        System.out.println("packageName: " + getPackageName() + ", " + mOutClassName);
//        StringBuilder builder = new StringBuilder();
//        builder.append("/***** Auto-generated by G7Anno. DO NOT modify! *****/\r\n\r\n");
//        builder.append("package ").append(getPackageName()).append(";\r\n\r\n");
//        builder.append("public class ").append(G7AnnoUtils.getProcessorSimpleName(mOutClassName))
//                .append("<T extends ").append(getEnclosingClassFullName());
//
//        if (mParentBindingClass == null) {
//            builder.append("> extends me.chunyu.g7anno.processor.ContextProcessor<T>")
//                    .append(" {\r\n\r\n");
//        } else {
//            builder.append("> extends ").append(mParentBindingClass.getPackageName())
//                    .append(".")
//                    .append(G7AnnoUtils
//                            .getProcessorSimpleName(mParentBindingClass.getOutClassName()))
//                    .append("<T>").append(" {\r\n\r\n");
//        }
//
//        /**
//         * 给放到{@link ActivityProcessor#getBroadcastIntentFilter(G7Activity)} 和
//         * {@link ActivityProcessor#onReceiveBroadcast(G7Activity, Intent)} 两个方法里进行处理的
//         * {@link me.chunyu.g7anno.annotation.BroadcastResponder} annotation也要单独建两个StringBuilder，
//         * 因为要生成两个方法。
//         */
//        StringBuilder intentFilterBuilder = new StringBuilder();
//        StringBuilder receiverBuilder = new StringBuilder();
//
//        //  第一遍循环，先处理需要单独写函数的annotation
//        for (String key : mElementMap.keySet()) {
//            List<Annotator> annotatorList = mElementMap.get(key);
//            for (Annotator annotator : annotatorList) {
//                System.out
//                        .println(annotator.getElementType().toString() + ", " +
//                                annotator.getSimpleName() + ", " +
//                                annotator.getAnnotation().annotationType().toString());
//                if (key.equals(BroadcastResponder.class.getCanonicalName())) {
//                    //  处理BroadcastResponder
//                    if (intentFilterBuilder.length() > 0) {
//                        intentFilterBuilder.append(",");
//                    }
//                    intentFilterBuilder
//                            .append(CodeGenerater.generateIntentFilterCommand(annotator));
//                    receiverBuilder
//                            .append(CodeGenerater.generateReceiveBroadcastCommand(annotator, "t"));
//                }
//            }
//        }
//
//        String filterStr = intentFilterBuilder.toString();
//        if (filterStr.length() > 0) {
////            filterStr = String.format(Locale.getDefault(),
////                    "\r\n@Override\r\nprotected android.content.IntentFilter getBroadcastIntentFilter(T t) {\r\n" +
////                            "\tandroid.content.IntentFilter filter = super.getBroadcastIntentFilter(t);\r\n" +
////                            "\tif (filter == null) {\r\n\tfilter = new android.content.IntentFilter();\r\n\t}\r\n" +
////                            "%s\r\n\r\nreturn filter;\r\n}\r\n\r\n", filterStr);
//            filterStr = String.format(Locale.getDefault(),
//                    "\r\n@Override\r\nprotected java.lang.String[] getBroadcastActions(T t) {\r\n" +
//                            "return new java.lang.String[] {%s};\r\n}\r\n\r\n", filterStr);
//            builder.append(filterStr);
//        }
//
//        String receiverStr = receiverBuilder.toString();
//        if (receiverStr.length() > 0) {
//            receiverStr = String.format(Locale.getDefault(),
//                    "\r\n@Override\r\nprotected void onReceiveBroadcastInternal(T t, android.content.Intent intent) {\r\n" +
//                            "\tString action = intent.getAction();\r\n" +
//                            "\tswitch (action) {\r\n%s\r\n}\r\n}\r\n\r\n", receiverStr);
//            builder.append(receiverStr);
//        }
//
//        builder.append("}");
//
//        return builder.toString();
//    }
//}
