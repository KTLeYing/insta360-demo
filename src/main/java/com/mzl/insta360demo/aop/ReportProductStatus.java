package com.mzl.insta360demo.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 上报商品状态注解
 * @Author: mzl
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReportProductStatus {

    String productName() default "";

    boolean isOnline() default true;

    int num() default 0;

}
