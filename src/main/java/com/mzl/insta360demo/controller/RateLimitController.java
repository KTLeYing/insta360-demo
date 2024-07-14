package com.mzl.insta360demo.controller;

import com.mzl.insta360demo.base.Response;
import com.mzl.insta360demo.ratelimit1.LimitType;
import com.mzl.insta360demo.ratelimit1.RateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 接口限流测试类
 * @Author: mzl
 */
@RestController
@RequestMapping("/rateLimit")
public class RateLimitController {

    @GetMapping("/rateLimitTest")
    @RateLimit(description = "限流测试(IP)接口", limitType = LimitType.IP, count = 5, period = 1, unit = TimeUnit.SECONDS)
    public Response<Void> rateLimitTest(){
        return Response.success();
    }

    @GetMapping("/rateLimitTest1")
    @RateLimit(description = "限流测试(TOKEN)接口", limitType = LimitType.TOKEN, count = 5, period = 1, unit = TimeUnit.SECONDS)
    public Response<Void> rateLimitTest1(){
        return Response.success();
    }

}