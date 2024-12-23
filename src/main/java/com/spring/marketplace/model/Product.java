package com.spring.marketplace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.AccessLevel;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
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
    @Enumerated(EnumType.STRING)
    private Categories category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private BigInteger quantity;

    @Column(name = "sku")
    private String sku;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "quantity_update")
    private Instant updatedAt;
}
