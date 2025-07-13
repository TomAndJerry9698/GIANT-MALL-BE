package com.tomandjerry.giantmall.cart.dto;

import com.tomandjerry.giantmall.cart.Cart;
import lombok.Getter;

@Getter
public class CartResponseDto {

    private Long cartId;
    private Long productId;
    private String productName;
    private int productPrice;
    private int quantity;
    private int totalPrice;

    public CartResponseDto(Cart cart) {
        this.cartId = cart.getId();
        this.productId = cart.getProduct().getId();
        this.productName = cart.getProduct().getName();
        this.productPrice = cart.getProduct().getPrice();
        this.quantity = cart.getQuantity();
        this.totalPrice = cart.getQuantity() * cart.getProduct().getPrice();
    }
}
