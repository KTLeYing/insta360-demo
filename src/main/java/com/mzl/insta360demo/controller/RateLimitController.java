package com.mzl.insta360demo.controller;

import com.mzl.insta360demo.base.Response;
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
    @RateLimit(description = "限流测试接口", count = 10, period = 1, unit = TimeUnit.SECONDS)
    public Response<Void> rateLimitTest(){
        return Response.success();
    }

}