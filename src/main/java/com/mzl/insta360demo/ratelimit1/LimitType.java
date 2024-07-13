package com.mzl.insta360demo.ratelimit1;

/**
 * @Description: 限流的维度类型
 * @Author: mzl
 */
public enum LimitType {

    /**
     * 根据TOKEN限制
     */
    TOKEN,

    /**
     *  根据IP地址限制
     */
    IP

}