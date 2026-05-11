package com.codewithmosh.store.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存值
     */
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue()
                .set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 取值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 原子setnx
     */
    public Boolean setIfAbsent(String key,
                               Object value,
                               long timeout) {

        return redisTemplate.opsForValue()
                .setIfAbsent(
                        key,
                        value,
                        timeout,
                        TimeUnit.SECONDS
                );
    }
}