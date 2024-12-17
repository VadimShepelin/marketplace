package com.spring.marketplace.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table("products")
@ToString(exclude = "category")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Product {

    @Id
    @GeneratedValue
    @Column("id")
    private UUID id;

    @Column("name")
    @NotBlank
    private String name;

    @Column("description")
    @NotBlank
    private String description;

    @Column("category_id")
    private int category;

    @Column("price")
    private double price;

    @Column("quantity")
    private BigInteger quantity;

    @Column("sku")
    @NotBlank
    private String sku;

    @Column("creation_date")
    private Instant createdAt;

    @Column("updated_at")
    private LocalDateTime updated_at;
}
