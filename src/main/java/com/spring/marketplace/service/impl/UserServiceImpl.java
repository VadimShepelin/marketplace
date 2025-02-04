package com.spring.marketplace.service.impl;

import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.model.User;
import com.spring.marketplace.repository.UserRepository;
import com.spring.marketplace.service.UserService;
import com.spring.marketplace.utils.enums.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() ->{
                    log.error("User with this id not found");
                    return new ApplicationException(ErrorType.NO_SUCH_USER);
                });
    }

    @Override
    @Transactional
    public void updateUserBalance(UUID userId, BigDecimal balance) {
        userRepository.updateBalance(balance,userId);
        log.info("Update user balance successfully");
    }
}
