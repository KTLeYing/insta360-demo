package com.mzl.insta360demo.ratelimit;

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
public @interface RateLimit1 {

    /**
     * 每秒允许的令牌数
     * @return
     */
    int permitsPerSecond() default 0;

    /**
     * 获取令牌的超时时间
     * @return
     */
    int timeout() default 0;

    /**
     * 超时时间单位，默认：ms
     * @return
     */
    TimeUnit timeoutUnit() default TimeUnit.MILLISECONDS;


}