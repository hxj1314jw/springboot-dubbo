package com.spring.app.config.cache;

import com.spring.common.utils.MD5Util;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: huangxj
 * DATE: 2018/1/19 15:36
 * Description:
 */
@Configuration
public class CachingConfig extends CachingConfigurerSupport {
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(".").append(method.getName());
                StringBuilder paramsSb = new StringBuilder();
                for (Object param : params) {
                    // 如果不指定，默认生成包含到键值中
                    if (param != null) {
                        paramsSb.append(param.toString());
                    }
                }
                if (paramsSb.length() > 0) {
                    sb.append("_").append(paramsSb);
                }
                return MD5Util.MD5(sb.toString());
            }
        };
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, String> redisTemplate, RedisKeys redisKeys) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(1800);// 30分钟
        // 根据key设定具体的缓存时间，key统一放在常量类RedisKeys中
        redisCacheManager.setExpires(redisKeys.getExpiresMap());
        List<String> cacheNames = new ArrayList<String>(redisKeys.getExpiresMap().keySet());
        redisCacheManager.setCacheNames(cacheNames);
        return redisCacheManager;
    }
}
