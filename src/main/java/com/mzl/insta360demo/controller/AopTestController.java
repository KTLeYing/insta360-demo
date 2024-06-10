package com.mzl.insta360demo.controller;

import com.mzl.insta360demo.aop.ReportProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: Aop测试控制器
 * @Author: mzl
 */
@RestController
@RequestMapping("/aopTest")
public class AopTestController {

    private static final Logger log = LoggerFactory.getLogger(AopTestController.class);

    @ReportProductStatus(productName = "Insta360 X4", num = 10, isOnline = true)
    @PostMapping("/reportX4Status")
    public String reportX4Status(@RequestParam("productName") String productName){
        log.info("执行reportX4Status方法...");
        return "success";
    }

    @ReportProductStatus(productName = "Insta360 X3", num = 2, isOnline = false)
    @PostMapping("/reportX3Status")
    public String reportX3Status(@RequestParam("productName") String productName){
        log.info("执行reportX3Status方法...");
        return "success";
    }

}