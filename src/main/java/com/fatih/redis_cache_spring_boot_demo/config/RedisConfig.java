package com.fatih.redis_cache_spring_boot_demo.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis configuration class.
 * Manages Redis connection, caching mechanism, and data serialization.
 */
@RequiredArgsConstructor
@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * Creates a JSON serializer for Redis.
     * Used to store objects in JSON format.
     *
     * @return RedisSerializer in JSON format
     */
    @Bean
    public RedisSerializer<Object> redisJsonSerializer() {
        ObjectMapper mapper = new ObjectMapper(); // Creates an ObjectMapper for JSON processing.
        mapper.registerModule(new JavaTimeModule()); // Supports Java 8 date and time objects.
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Stores dates in ISO format instead of timestamps.
        mapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        ); // Enables storing type information for objects in JSON to ensure correct deserialization.
        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    /**
     * Creates the Redis connection factory.
     * Provides connection to the Redis server.
     *
     * @return RedisConnectionFactory instance
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379); // Connects to Redis at localhost:6379.
    }

    /**
     * Configures RedisTemplate.
     * Manages data exchange with Redis.
     *
     * @return RedisTemplate instance
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // Stores keys in string format.
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class)); // Stores hash keys in string format.
        redisTemplate.setValueSerializer(redisJsonSerializer()); // Stores values in JSON format.
        redisTemplate.setHashValueSerializer(redisJsonSerializer()); // Stores hash values in JSON format.
        redisTemplate.setConnectionFactory(redisConnectionFactory()); // Uses the Redis connection.
        return redisTemplate;
    }

    /**
     * Creates the Redis cache manager (CacheManager).
     * Enables caching with Redis using Spring Cache.
     *
     * @param redisConnectionFactory Redis connection factory
     * @return CacheManager instance
     */
    @Bean(value = "cacheManager")
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() // Disables caching of null values.
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(redisJsonSerializer())
                ); // Stores values in JSON format
        redisCacheConfiguration.usePrefix(); // Adds a prefix to cache keys to prevent collisions.

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration) // Applies default cache configuration.
                .build(); // Builds the CacheManager instance.
    }
}
