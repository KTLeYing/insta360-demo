package com.mzl.insta360demo.ratelimit;

import com.mzl.insta360demo.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 限流切面逻辑
 * @Author: mzl
 */
@Aspect
@Component
public class RateLimitAspect1 {

    private static final Logger log = LoggerFactory.getLogger(RateLimitAspect1.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 限流的缓存Key
     */
    public static final String RATE_LIMIT_PREFIX = "rateLimit:";

    @Around("@annotation(rateLimit1)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimit1 rateLimit1) throws Throwable {
        String key = getRateLimitKey();
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 设置令牌桶的速率为每秒生成 permitsPerSecond 个令牌，应用于整个 Redis 集群; 限流时间间隔为1秒
        rateLimiter.trySetRate(RateType.OVERALL, rateLimit1.permitsPerSecond(), 1L, RateIntervalUnit.SECONDS);

        log.info("尝试获取请求...");
        // 从令牌桶中获取一个令牌，并在指定的超时时间内等待。如果在超时时间内获取到令牌，则请求成功，否则请求拒绝
        boolean acquired = rateLimiter.tryAcquire(rateLimit1.timeout(), rateLimit1.timeoutUnit());
        log.info("尝试获取请求结果，acquired={}", acquired);

        if (Boolean.FALSE.equals(acquired)){
            throw new BusinessException(-1, "请求太频繁，请稍后继续...");
        }
        log.info("限流放行成功...限流key={}", key);

        return joinPoint.proceed();
    }

    /**
     * 获取限流的key，使用token或ip
     *
     * @return key
     */
    private String getRateLimitKey() {
        String key = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isEmpty(key)){
            key = httpServletRequest.getRemoteAddr();
        }

        return RATE_LIMIT_PREFIX + key;
    }

}