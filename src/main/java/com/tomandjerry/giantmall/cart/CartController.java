package com.tomandjerry.giantmall.cart;

import com.tomandjerry.giantmall.cart.dto.CartDeleteRequestDto;
import com.tomandjerry.giantmall.cart.dto.CartListResponseDto;
import com.tomandjerry.giantmall.cart.dto.CartRequestDto;
import com.tomandjerry.giantmall.cart.dto.CartResponseDto;
import com.tomandjerry.giantmall.cart.dto.CartUpdateQuantityDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 1. 장바구니 담기
    @PostMapping
    public ResponseEntity<CartResponseDto> addToCart(
        @RequestBody @Valid CartRequestDto requestDto) {
        CartResponseDto response = cartService.addToCart(requestDto);
        return ResponseEntity.ok(response);
    }

    // 2. 사용자 장바구니 조회 (총 금액 포함)
    @GetMapping("/{userId}")
    public ResponseEntity<CartListResponseDto> getUserCart(@PathVariable Long userId) {
        List<CartResponseDto> cartList = cartService.getUserCart(userId);
        return ResponseEntity.ok(new CartListResponseDto(cartList));
    }

    // 3. 수량 변경
    @PatchMapping("/{cartId}/quantity")
    public ResponseEntity<CartResponseDto> updateQuantity(
        @PathVariable Long cartId,
        @RequestBody @Valid CartUpdateQuantityDto dto) {
        CartResponseDto response = cartService.updateQuantity(cartId, dto.getQuantity());
        return ResponseEntity.ok(response);
    }

    // 4. 장바구니 항목 삭제
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartId) {
        cartService.deleteCartItem(cartId);
        return ResponseEntity.noContent().build();
    }

    // 5. 장바구니 전체 비우기
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    // 6. 선택 항목 삭제
    @DeleteMapping("/{userId}/batch")
    public ResponseEntity<Void> deleteCartItems(
        @PathVariable Long userId,
        @RequestBody @Valid CartDeleteRequestDto requestDto) {

        cartService.deleteCartItems(userId, requestDto.getCartIds());
        return ResponseEntity.noContent().build();
    }
}