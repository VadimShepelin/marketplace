package com.spring.marketplace.repository;

import com.spring.marketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
    @Modifying
    @Query("update Product p " +
            "set p.price = p.price * 1.10")
    void updateAllProductsPrice();
    Optional<Product> findBySku(String sku);
}
