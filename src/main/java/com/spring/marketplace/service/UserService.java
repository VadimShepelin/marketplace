package com.spring.marketplace.service;

import com.spring.marketplace.dto.GetUserResponse;
import com.spring.marketplace.model.User;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);

    void updateUserBalance(UUID id, BigDecimal balance);

    List<GetUserResponse> getAllUsers();
}
