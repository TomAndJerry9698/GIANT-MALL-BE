package com.tomandjerry.giantmall.product;

import com.tomandjerry.giantmall.common.exception.CustomException;
import com.tomandjerry.giantmall.common.exception.ErrorCode;
import com.tomandjerry.giantmall.product.dto.ProductCreateDto;
import com.tomandjerry.giantmall.product.dto.ProductResponseDto;
import com.tomandjerry.giantmall.product.dto.ProductUpdateDto;
import com.tomandjerry.giantmall.user.User;
import com.tomandjerry.giantmall.user.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(User user, ProductCreateDto dto) {

        validateRolePermission(user);
        Product product = dto.toEntity(user);
        Product saved = productRepository.save(product);
        return ProductResponseDto.toDto(saved);
    }

    @Transactional
    public ProductResponseDto updateProduct(User user, Long id, ProductUpdateDto dto) {
        Product product = findById(id);
        validateProductOwnerPermission(user, product);

        product.update(dto);
        return ProductResponseDto.toDto(product);
    }

    @Transactional
    public void deleteProduct(User user, Long id) {
        Product product = findById(id);
        validateProductOwnerPermission(user, product);

        productRepository.delete(product);
    }

    public ProductResponseDto getProduct(Long id) {
        Product product = findById(id);
        return ProductResponseDto.toDto(product);
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(ProductResponseDto::toDto)
                .collect(Collectors.toList());
    }

    private void validateRolePermission(User user) {
        boolean isSeller = user.getRole() == UserRole.SELLER;
        boolean isAdmin = user.getRole() == UserRole.ADMIN;
        if(!(isSeller || isAdmin)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

    private void validateProductOwnerPermission(User user, Product product) {
        boolean isOwner = product.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == UserRole.ADMIN;

        if(!(isOwner || isAdmin)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}