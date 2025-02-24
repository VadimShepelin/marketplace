package com.spring.marketplace.service.impl;

import com.spring.marketplace.service.IdempotencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("local")
public class InMemoryIdempotencyServiceImpl implements IdempotencyService {
    private final ConcurrentHashMap<String, String> localCache;

    @Override
    public String processOrderRequest(UUID idempotencyKey) {
        log.info("call InMemoryIdempotencyServiceImpl.processOrderRequest with idempotencyKey: {}", idempotencyKey);
        localCache.computeIfAbsent(idempotencyKey.toString(), value -> UUID.randomUUID().toString());

        return localCache.get(idempotencyKey.toString());
    }
}
