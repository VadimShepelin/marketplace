package com.spring.marketplace.service.impl;

import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.service.IdempotencyService;
import com.spring.marketplace.utils.enums.ErrorType;
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
    public String processOrderRequest(UUID idempotencyKey) {
        log.info("call RedisIdempotencyServiceImpl.processOrderRequest with idempotencyKey: {}",idempotencyKey);

        try {
            ValueOperations<String, String> redisValue = stringRedisTemplate.opsForValue();
            redisValue.setIfAbsent(idempotencyKey.toString(), UUID.randomUUID().toString());

            return redisValue.get(idempotencyKey.toString());
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ApplicationException(ErrorType.REDIS_EXCEPTION);
        }
    }
}
