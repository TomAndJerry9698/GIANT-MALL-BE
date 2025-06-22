package com.tomandjerry.giantmall.product;

import com.tomandjerry.giantmall.product.dto.ProductCreateDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Product(String name, String description, int price, ProductStatus status, Long sellerId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.sellerId = sellerId;
    }

    public void update(ProductCreateDto dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
    }
}