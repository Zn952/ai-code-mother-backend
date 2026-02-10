package com.zn.aicodemother.config;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;

/**
 * @program: partner-matching-backend
 * @description: redis序列化配置
 * @author: Zn
 * @create: 2026-01-11 10:35
 **/
@Configuration
public class RedisTemplateConfig {

    /**
     * 自定义FastJSON2 Redis序列化器（核心：实现RedisSerializer接口）
     * 替代弃用的GenericFastJson2RedisSerializer
     */
    public static class FastJson2RedisSerializer<T> implements RedisSerializer<T> {
        private final Class<T> clazz;
        // FastJSON2序列化/反序列化特性配置（参考官方推荐）
        private static final JSONWriter.Feature[] WRITE_FEATURES = {
                JSONWriter.Feature.WriteNulls,                // 输出null值字段
                JSONWriter.Feature.WriteMapNullValue,        // Map类型保留null值
                JSONWriter.Feature.ReferenceDetection,       // 支持循环引用
                JSONWriter.Feature.WriteClassName  // 添加类名信息
        };
        private static final JSONReader.Feature[] READ_FEATURES = {
                JSONReader.Feature.IgnoreNullPropertyValue, // null值按默认值处理
                JSONReader.Feature.IgnoreAutoTypeNotMatch,   // 忽略类型不匹配的自动类型
                JSONReader.Feature.SupportAutoType  // 支持自动类型识别
        };

        // 构造器：指定序列化的目标类型
        public FastJson2RedisSerializer(Class<T> clazz) {
            this.clazz = clazz;
        }

        /**
         * 序列化：对象 → 字节数组（Redis存储格式）
         */
        @Override
        public byte[] serialize(T t) {
            if (t == null) {
                return new byte[0]; // 空对象返回空字节数组，避免null
            }
            // 使用FastJSON2将对象转为JSON字符串，再转字节数组（UTF-8编码）
            return JSON.toJSONString(t, WRITE_FEATURES).getBytes(StandardCharsets.UTF_8);
        }

        /**
         * 反序列化：字节数组 → 对象
         */
        @Override
        public T deserialize(byte[] bytes) {
            if (bytes == null || bytes.length == 0) {
                return null; // 空字节数组返回null
            }
            // 将字节数组转为JSON字符串，再解析为指定类型的对象
            String jsonStr = new String(bytes, StandardCharsets.UTF_8);
            return JSON.parseObject(jsonStr, clazz, READ_FEATURES);
        }
    }

    /**
     * 配置RedisTemplate，使用自定义FastJSON2序列化器
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 1. Key和HashKey：使用字符串序列化（保证Redis中Key可读）
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);

        // 2. Value和HashValue：使用自定义FastJSON2序列化器
        FastJson2RedisSerializer<Object> fastJson2Serializer = new FastJson2RedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(fastJson2Serializer);
        redisTemplate.setHashValueSerializer(fastJson2Serializer);

        // 3. 全局默认序列化器（兜底）
        redisTemplate.setDefaultSerializer(fastJson2Serializer);

        // 4. 初始化配置（必须调用，否则序列化器不生效）
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

//    @Bean
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
//        // **StringRedisTemplate 已经帮你配好 String 序列化器**
//        return new StringRedisTemplate(factory);
//    }

}
