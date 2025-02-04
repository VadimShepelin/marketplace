package com.spring.marketplace.service;

import com.spring.marketplace.model.User;
import java.math.BigDecimal;
import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
    void updateUserBalance(UUID id, BigDecimal balance);
}
