package com.spring.app.config.cache;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User: huangxj
 * DATE: 2018/1/19 15:36
 * Description:
 */
@Component
public class RedisKeys {
    // 根据key设定具体的缓存时间
    private Map<String, Long> expiresMap =  new HashMap<>();
    public static final String testExpire="testExpire_10s";
    public static final String tokenExpire="tokenExpire";

    /**
     * 初始化  默认单位:秒
     */
    @PostConstruct
    public void init(){
        expiresMap.put(testExpire, 60L);
        expiresMap.put(tokenExpire, 3600L);
    }

    public Map<String, Long> getExpiresMap(){
        return this.expiresMap;
    }
}
