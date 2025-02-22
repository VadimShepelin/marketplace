package com.spring.marketplace.service.impl;

import com.spring.marketplace.service.IdempotencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Profile("default")
public class RedisIdempotencyServiceImpl implements IdempotencyService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public String processOrderRequest(UUID key) {
        log.info("call RedisIdempotencyServiceImpl.processOrderRequest");
        ValueOperations<String, String> redisValue = stringRedisTemplate.opsForValue();
        redisValue.setIfAbsent(key.toString(), UUID.randomUUID().toString());

        return redisValue.get(key.toString());
    }
}
