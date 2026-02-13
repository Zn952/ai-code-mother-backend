package com.zn.aicodemother.ratelimit.enums;

/**
 * @program: ai-code-mother-backend
 * @description: ai请求限流枚举类
 * @author: Zn
 * @create: 2026-02-07 20:27
 **/
public enum RateLimitType {

    /**
     * 接口级别限流
     */
    API,

    /**
     * 用户级别限流
     */
    USER,

    /**
     * IP级别限流
     */
    IP
}

