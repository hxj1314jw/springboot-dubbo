package com.spring.app.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created with IntelliJ IDEA
 * User: huangxj
 * DATE: 2018/1/19 15:40
 * Description:
 */
@Slf4j
@Component
public class RedisService {
    @Autowired
    public RedisTemplate<String, String> redisTemplate;

    /**
     * 根据key从redis里面取数据
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object objectValue = redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] byteKey = key.getBytes();
                byte[] value = connection.get(byteKey);
                if (value == null) {
                    return null;
                }
                return toObject(value);
            }
        }, true);
        log.debug("Cache L2 get (redis) :{}={}", key, objectValue);
        return objectValue;
    }

    /**
     * 向redis里面存数据
     *
     * @param key
     * @param valueStr
     * @param liveTime  单位秒
     */
    public void put(final String key, final Object valueStr, long liveTime) {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyb = key.getBytes();
                byte[] valueb = new byte[0];
                valueb = toByteArray(valueStr);
                connection.set(keyb, valueb, Expiration.seconds(liveTime), RedisStringCommands.SetOption.UPSERT);
                log.debug("Cache L2 put (redis) :{}={}", key, valueb);
                if (liveTime > 0) {
                    connection.expire(keyb, liveTime);
                }
                return 1L;
            }
        }, true);
    }

    /**
     * 刪除所有redis緩存
     */
    public void deleteAll() {
        redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb();
                return "clear done.";
            }
        }, true);
    }

    /**
     * 刪除指定key
     *
     * @param key
     */
    public void deleteOne(String key) {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.del(key.getBytes());
            }
        }, true);
    }

    /**
     * 描述 : Object转byte[]. <br>
     *
     * @param obj
     * @return
     */
    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 描述 :  byte[]转Object . <br>
     *
     * @param bytes
     * @return
     */
    private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }
}
