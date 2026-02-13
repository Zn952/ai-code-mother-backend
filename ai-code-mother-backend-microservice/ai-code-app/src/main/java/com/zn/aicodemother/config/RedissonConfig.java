package com.zn.aicodemother.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: partner-matching-backend
 * @description: Redisson配置客户端
 * @author: Zn
 * @create: 2026-01-12 10:28
 **/

@Configuration
@Data
public class RedissonConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private String redisPort;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Value("${spring.data.redis.database}")
    private int redisDatabase;

    @Bean
    public RedissonClient redissonClient() {
        //1、创建配置
        Config config = new Config();
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%s", redisHost, redisPort))
                .setDatabase(redisDatabase)
                .setPassword(redisPassword)
                .setConnectionMinimumIdleSize(10)
                .setConnectionPoolSize(100)
                .setIdleConnectionTimeout(30000) // 30秒
                .setTimeout(3000) // 3秒
                .setRetryAttempts(3)
                .setRetryInterval(1500); // 1.5秒
        //2、创建实例
        return Redisson.create(config);
    }
}
