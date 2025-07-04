package com.tomandjerry.giantmall.product.dto;

import com.tomandjerry.giantmall.product.Product;
import com.tomandjerry.giantmall.product.ProductStatus;
import com.tomandjerry.giantmall.user.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductCreateDto {

    @NotBlank
    private String name;
    private String description;

    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    @NotNull
    private int price;

    @NotNull
    private Long userId;

    public Product toEntity(User user) {
        return Product.builder()
            .name(this.getName())
            .price(this.getPrice())
            .description(this.getDescription())
            .status(ProductStatus.AVAILABLE)
            .user(user)
            .build();
    }
}
