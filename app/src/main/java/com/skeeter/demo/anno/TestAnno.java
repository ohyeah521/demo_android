package com.skeeter.demo.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author michael created on 2016/12/2.
 */

@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface TestAnno {
    public String value() default "";
}
