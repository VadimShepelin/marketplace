package com.spring.marketplace.repository;

import com.spring.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("UPDATE User u " +
            "SET u.balance = u.balance - :balance " +
            "WHERE u.id = :id")
    @Modifying
    int updateBalance(BigDecimal balance, UUID id);
}
