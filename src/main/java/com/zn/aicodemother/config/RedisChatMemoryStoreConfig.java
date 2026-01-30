package com.zn.aicodemother.config;

import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ai-code-mother-backend
 * @description: Redis对话记忆存储配置类
 * @author: Zn
 * @create: 2026-01-30 16:59
 **/
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class RedisChatMemoryStoreConfig {

    private String host;

    private int port;

    private String username;

    private String password;

    private long ttl = 60 * 60L; // 默认1小时

    @Bean
    public RedisChatMemoryStore redisChatMemoryStore() {
        return RedisChatMemoryStore.builder()
                .host(host)
                .port(port)
                .password(password)
                .user(username)
                .ttl(ttl)
                .prefix("chat:memory:")
                .build();
    }
}

