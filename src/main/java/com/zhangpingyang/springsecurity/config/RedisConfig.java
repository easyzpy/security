package com.zhangpingyang.springsecurity.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName()).append(".");
                sb.append(method.getName()).append(",");
                for (Object obj : params) {
                    sb.append(obj.toString()).append(",");
                }
                return sb.toString();
            }
        };

    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        @SuppressWarnings({ "rawtypes", "unchecked" })
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
//    @Bean
//    public KeyGenerator wiselyKeyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public String generate(Object target, Method method, Object... param) {
//                StringBuilder sb = new StringBuilder();
////                sb.append(target.getClass().getName());
////                sb.append(method.getName());
//                for (Object object : param) {
//                    sb.append(object.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }
//
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        StringRedisSerializer redisSerializer = new StringRedisSerializer();
//
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        template.setConnectionFactory(factory);
//        //key序列化方式
//        template.setKeySerializer(redisSerializer);
//        //value序列化方式
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        //value hash序列化方式
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        return template;
//    }
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory) {
//        StringRedisSerializer redisSerializer = new StringRedisSerializer();
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = ObjectMapperUtil.getObjectMapper();
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        // 配置序列化（解决乱码的问题）,过期时间30秒
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(30))
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
//                .disableCachingNullValues();
//
//        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
//                .cacheDefaults(config)
//                .build();
//        return cacheManager;
//    }
}
