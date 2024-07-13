package com.mzl.insta360demo.ratelimit1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 限流注解
 * @Author: mzl
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 接口描述
     */
    String description() default "";

    /**
     * 限制访问次数
     */
    int count() default 0;

    /**
     * 限流的时间范围
     */
    int period() default 0;

    /**
     * 限流的时间单位，默认s
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 限制类型
     */
    LimitType limitType() default LimitType.IP;

}