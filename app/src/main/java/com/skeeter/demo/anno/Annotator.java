package com.skeeter.demo.anno;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * Created by eden on 5/4/15.
 */
public class Annotator {

    public Annotator(Element element, Annotation annotation) {
        mElement = element;
        mAnnotation = annotation;
    }

    /**
     * 被修饰的域的element，如果修饰的是对象自身而不是对象中的域，
     * 这个值为null
     */
    private Element mElement;

    /**
     * 用于修饰这个域的annotation
     */
    private Annotation mAnnotation;

    public TypeMirror getElementType() {
        return mElement.asType();
    }

    public Element getElement() {
        return mElement;
    }

    public String getSimpleName() {
        return mElement.getSimpleName().toString();
    }

    public Annotation getAnnotation() {
        return mAnnotation;
    }

    @Override
    public String toString() {
        return String.format("Annotator: annoType=%s, simpleName=%s, annotation=%s",
                getElementType().toString(), getSimpleName(), mAnnotation.toString());
    }
}

