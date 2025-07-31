package com.tomandjerry.giantmall.cart;

import com.tomandjerry.giantmall.cart.dto.CartRequestDto;
import com.tomandjerry.giantmall.cart.dto.CartResponseDto;
import com.tomandjerry.giantmall.common.exception.CustomException;
import com.tomandjerry.giantmall.common.exception.ErrorCode;
import com.tomandjerry.giantmall.product.Product;
import com.tomandjerry.giantmall.product.ProductRepository;
import com.tomandjerry.giantmall.user.User;
import com.tomandjerry.giantmall.user.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartResponseDto addToCart(CartRequestDto dto) {
        User user = findUserById(dto.getUserId());
        Product product = findProductById(dto.getProductId());

        Cart cart = cartRepository.findByUserAndProduct(user, product)
            .orElse(Cart.builder()
                .user(user)
                .product(product)
                .quantity(0)
                .build());

        cart.updateQuantity(cart.getQuantity() + dto.getQuantity());
        Cart savedCart = cartRepository.save(cart);

        return new CartResponseDto(savedCart);
    }

    public List<CartResponseDto> getUserCart(Long userId) {
        User user = findUserById(userId);
        return cartRepository.findAllByUser(user).stream()
            .map(CartResponseDto::new)
            .collect(Collectors.toList());
    }

    public CartResponseDto updateQuantity(Long cartId, int quantity) {
        Cart cart = findCartById(cartId);
        cart.updateQuantity(quantity);
        return new CartResponseDto(cart);
    }

    public void deleteCartItem(Long cartId) {
        Cart cart = findCartById(cartId);
        cartRepository.delete(cart);
    }

    public void clearCart(Long userId) {
        User user = findUserById(userId);
        cartRepository.deleteAllByUser(user);
    }

    public void deleteCartItems(Long userId, List<Long> cartIds) {
        List<Cart> carts = cartRepository.findAllById(cartIds);
        List<Cart> userCarts = carts.stream()
            .filter(cart -> cart.getUser().getId().equals(userId))
            .toList();

        if (userCarts.isEmpty()) {
            throw new CustomException(ErrorCode.CART_ITEM_NOT_FOUND);
        }

        cartRepository.deleteAll(userCarts);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private Cart findCartById(Long cartId) {
        return cartRepository.findById(cartId)
            .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));
    }
}