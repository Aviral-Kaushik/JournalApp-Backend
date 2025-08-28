package com.aviral.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public <T>T get(String key, Class<T> tClass) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) return null;
        return tClass.cast(value);
    }

    public void set(String key, Object value, Long timeToExpiry) {
        System.out.println("Setting value in redis: " + value + " for key: " + key);
        redisTemplate.opsForValue().set(key, value, timeToExpiry, TimeUnit.SECONDS);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
