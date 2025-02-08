package com.spring.marketplace.repository;

import com.spring.marketplace.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query(value = "SELECT * FROM orders WHERE orders.id = ?",nativeQuery = true)
    Optional<Order> findOrderById(UUID orderId);
}