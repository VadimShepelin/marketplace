package com.spring.marketplace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.AccessLevel;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "products")
@ToString(exclude = "category")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name="name")
    private String name;

    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "category")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Categories category;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private BigInteger quantity;

    @Column(name = "sku")
    private String sku;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "quantity_update")
    private LocalDateTime updatedAt;
}
