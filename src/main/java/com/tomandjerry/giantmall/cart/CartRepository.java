package com.tomandjerry.giantmall.cart;

import com.tomandjerry.giantmall.product.Product;
import com.tomandjerry.giantmall.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUser(User user);

    Optional<Cart> findByUserAndProduct(User user, Product product);

    void deleteAllByUser(User user);
}
