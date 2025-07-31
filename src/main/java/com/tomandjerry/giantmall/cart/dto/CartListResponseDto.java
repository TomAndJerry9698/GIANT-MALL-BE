package com.tomandjerry.giantmall.cart.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class CartListResponseDto {

    private final List<CartResponseDto> items;
    private final int totalAmount;

    public CartListResponseDto(List<CartResponseDto> items) {
        this.items = items;
        this.totalAmount = items.stream()
            .mapToInt(CartResponseDto::getTotalPrice)
            .sum();
    }
}