package com.tomandjerry.giantmall.product.dto;

import com.tomandjerry.giantmall.product.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private String description;
    private int price;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String username;

    public static ProductResponseDto toDto(Product entity) {
        return ProductResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .price(entity.getPrice())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .username(entity.getUser().getName())
            .build();
    }
}
