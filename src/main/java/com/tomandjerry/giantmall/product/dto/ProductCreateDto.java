package com.tomandjerry.giantmall.product.dto;

import com.tomandjerry.giantmall.product.Product;
import com.tomandjerry.giantmall.product.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProductCreateDto {

    @NotBlank
    private String name;
    private String description;

    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    @NotBlank
    private int price;

    private Long sellerId;

    public Product toEntity() {
        return Product.builder()
            .name(this.getName())
            .price(this.getPrice())
            .description(this.getDescription())
            .status(ProductStatus.AVAILABLE)
            .sellerId(sellerId)
            .build();
    }
}
