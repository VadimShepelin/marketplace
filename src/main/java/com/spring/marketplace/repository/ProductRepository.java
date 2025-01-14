package com.spring.marketplace.repository;

import com.spring.marketplace.model.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    @Query(value = "select p from Product p")
    @Lock(LockModeType.OPTIMISTIC)
    List<Product> findAllProductsAndLock();

    Optional<Product> findBySku(String sku);
}
