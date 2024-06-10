package com.mzl.insta360demo.aop;

import com.mzl.insta360demo.infrastructure.mapper.UserMapper;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @Description: 上报商品状态切面处理
 * @Author: mzl
 */
@Aspect
@Component
public class ReportProductStatusAspect {

    private static final Logger log = LoggerFactory.getLogger(ReportProductStatusAspect.class);

    @Resource
    private UserMapper userMapper;

    @AfterReturning(value = "@annotation(reportProductStatus)")
    public void process(ReportProductStatus reportProductStatus) throws Throwable {
        log.info("上报商品状态切面开始处理...");
        try {
            // 模拟真正处理
            Thread.sleep(3000);
            String productName = reportProductStatus.productName();
            Boolean isOnline = reportProductStatus.isOnline();
            Integer num = reportProductStatus.num();
            log.info("上报商品状态切面正在处理, productName={}, isOnline={}, num={}", productName, isOnline, num);
        } catch (Exception e) {
            log.error("上报商品状态切面处理失败...", e);
        }

        log.info("上报商品状态切面处理结束...");
    }

}