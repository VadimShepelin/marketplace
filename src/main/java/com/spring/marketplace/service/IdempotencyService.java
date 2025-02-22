package com.spring.marketplace.service;

import java.util.UUID;

public interface IdempotencyService {

    String processOrderRequest(UUID key);
}
