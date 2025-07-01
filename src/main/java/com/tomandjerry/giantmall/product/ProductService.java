package com.tomandjerry.giantmall.product;

import com.tomandjerry.giantmall.common.exception.CustomException;
import com.tomandjerry.giantmall.common.exception.ErrorCode;
import com.tomandjerry.giantmall.product.dto.ProductCreateDto;
import com.tomandjerry.giantmall.product.dto.ProductResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductCreateDto dto) {
        Product product = dto.toEntity();
        Product saved = productRepository.save(product);
        return ProductResponseDto.toDto(saved);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductCreateDto dto) {
        Product product = findById(id);
        product.update(dto);
        return ProductResponseDto.toDto(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = findById(id);
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

    private Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}