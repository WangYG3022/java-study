package com.wang.weadmin.config;

import com.wang.weadmin.config.serializer.FastJson2JsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @description: redis配置类
 * @author: WANG Y.G.
 * @time: 2019/12/18 10:01
 * @version: V1.0
 */
@EnableCaching
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    /**
     * 选择redis作为默认缓存工具
     * SpringBoot 2.0 以上版本的配置
     * @param factory
     * @return
     */
    @Bean(name = "cacheManager")
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1L))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJson2JsonRedisSerializer()))
                .disableCachingNullValues();

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    /**
     * redisTemplate相关配置
     * @param factory
     * @return
     */
    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 配置连接工厂
        redisTemplate.setConnectionFactory(factory);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(stringRedisSerializer());
        // value采用json序列化
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());
        // 设置hash key 和value序列化模式
        redisTemplate.setHashKeySerializer(stringRedisSerializer());
        redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return  redisTemplate;
    }

    /**
     * stringRedisTemplate相关配置
     * @param factory
     * @return
     */
    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        return stringRedisTemplate;
    }

    /**
     * 使用StringRedisSerializer来序列化和反序列化redis的key值
     * @return StringRedisSerializer
     */
    private StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    /**
     * 使用FastJson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
     * @return FastJson2JsonRedisSerializer<Object>
     */
    private FastJson2JsonRedisSerializer<Object> fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<>(Object.class);
    }

}
