package com.mzl.insta360demo.ratelimit1;


import com.mzl.insta360demo.exception.BusinessException;
import com.mzl.insta360demo.util.IPUtil;
import com.mzl.insta360demo.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;

/**
 * @Description: 限流切面逻辑
 * @Author: mzl
 */
@Aspect
@Component
public class RateLimitAspect {

    private static final Logger log = LoggerFactory.getLogger(RateLimitAspect.class);

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    public static final String KEY_PREFIX = "rateLimit:";

    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String limitFlag = getLimitFlag(rateLimit, request);
        if (StringUtils.isEmpty(limitFlag)){
            log.info("限流的标识为空, 放行请求...");
            joinPoint.proceed();
        }

        String method = joinPoint.getSignature().toString();
        String params = method + ";" + limitFlag;
        String paramsMd5 = Md5Util.generateMD5(params);
        String key = KEY_PREFIX + paramsMd5;

        // int limitCount = rateLimit.count();
        int limitCount = 10;
        // 时间转换为秒，即缓存过期时间
        // long limitPeriod = rateLimit.unit().toSeconds(rateLimit.period());
        int limitPeriod = 1;

        String luaScript = buildLuaScript();
        RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
        Number count = redisTemplate.execute(redisScript, Collections.singletonList(key), 10, 1);

        log.info("用户={}, 第 {} 次访问接口 {}, 限流参数params={}, 限流加密key={}", limitFlag, count, rateLimit.description(), params, key);

        if (Objects.isNull(count) || count.intValue() > limitCount) {
            throw new BusinessException(-1, "请求太频繁，请稍后继续...");
        }

        return joinPoint.proceed();
    }

    /**
     * 限流脚本
     * 调用的时候不超过阈值，则直接返回并执行计算器自加。
     * key过期时间单位为 秒(s)
     * @return lua脚本
     */
    private String buildLuaScript() {
        return "local c" +
                "\nc = redis.call('get',KEYS[1])" +
                "\nif c and tonumber(c) > tonumber(ARGV[1]) then" +
                "\nreturn c;" +
                "\nend" +
                "\nc = redis.call('incr',KEYS[1])" +
                "\nif tonumber(c) == 1 then" +
                "\nredis.call('expire',KEYS[1],ARGV[2])" +
                "\nend" +
                "\nreturn c;";
    }

    private String getLimitFlag(RateLimit rateLimit, HttpServletRequest request) {
        LimitType limitType = rateLimit.limitType();
        switch (limitType) {
            case IP:
                return IPUtil.getIpAddress(request);
            case TOKEN:
                return request.getHeader("Authorization");
            default:
                return "";
        }
    }

}