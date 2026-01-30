package com.zn.aicodemother.ai;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zn.aicodemother.service.ChatHistoryService;
import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @program: ai-code-mother-backend
 * @description: 初始化AI服务
 * @author: Zn
 * @create: 2026-01-20 17:01
 **/
@Slf4j
@Configuration
public class AiCodeGeneratorServiceFactory {
    @Resource
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel streamingChatModel;

    @Resource
    private RedisChatMemoryStore redisChatMemoryStore;

    @Resource
    private ChatHistoryService chatHistoryService;

//    @Bean
//    public AiCodeGeneratorService aiCodeGeneratorService() {
//        return AiServices.create(AiCodeGeneratorService.class, chatModel);
//    }

    /**
     * 缓存AI代码生成器服务实例
     * 缓存策略
     * - 最大缓存大小：1000
     * - 缓存写入过期时间：30分钟
     * - 缓存访问过期时间：10分钟
     * - 缓存移除监听器：记录日志
     */
    private final Cache<Long, AiCodeGeneratorService> serviceCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofMinutes(30))
            .expireAfterAccess(Duration.ofMinutes(10))
            .removalListener(((key, value, cause) -> {
                log.info("AiCodeGeneratorService removed from cache, key: {}, value: {}, cause: {}", key, value, cause);
            }
            ))
            .build();

    /**
     * 根据应用ID获取AI代码生成器服务
     * 如果缓存中不存在，则通过创建函数创建新的服务实例
     *
     * @param appId 应用的唯一标识符
     * @return 返回对应appId的AiCodeGeneratorService实例
     */
    public AiCodeGeneratorService getAiCodeGeneratorService(long appId) {
        // 使用serviceCache的get方法，如果缓存中不存在appId对应的service，则调用createAiCodeGeneratorService方法创建
        return serviceCache.get(appId, this::createAiCodeGeneratorService);
    }


    /**
     * 根据应用ID获取AI代码生成器服务
     * 该方法用于返回指定应用ID对应的AI代码生成器服务实例
     *
     * @param appId 应用ID，用于标识特定的应用
     * @return AiCodeGeneratorService 返回AI代码生成器服务实例
     */
    private AiCodeGeneratorService createAiCodeGeneratorService(long appId) {
        log.info("为appId: {} 创建 AI服务实例", appId);
        // 根据应用ID构建独立的对话记忆
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .id(appId)
                .chatMemoryStore(redisChatMemoryStore)
                .maxMessages(20)
                .build();
        chatHistoryService.loadChatHistoryToMemory(appId,chatMemory,20);
        // 创建一个ChatMemoryProvider，总是返回这个chatMemory
        ChatMemoryProvider chatMemoryProvider = memoryId -> chatMemory;
        return AiServices.builder(AiCodeGeneratorService.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .build();
    }

    @Bean
    public AiCodeGeneratorService aiCodeGeneratorService() {
        return getAiCodeGeneratorService(0l);
    }

}
