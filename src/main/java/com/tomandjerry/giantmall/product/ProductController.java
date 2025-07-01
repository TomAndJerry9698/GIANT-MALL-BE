package com.tomandjerry.giantmall.product;

import com.tomandjerry.giantmall.common.dto.ApiResponseDto;
import com.tomandjerry.giantmall.product.dto.ProductCreateDto;
import com.tomandjerry.giantmall.product.dto.ProductResponseDto;
import com.tomandjerry.giantmall.user.CustomUserDetails;
import com.tomandjerry.giantmall.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품 API", description = "상품 등록/조회/수정/삭제 관련 API")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "판매자가 새로운 상품을 등록합니다.")
    @PostMapping
    public ApiResponseDto<ProductResponseDto> createProduct(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody @Valid ProductCreateDto dto
    ) {
        User user = userDetails.getUser();
        ProductResponseDto response = productService.createProduct(user, dto);
        return ApiResponseDto.success(response, "상품이 등록되었습니다.");
    }

    @Operation(summary = "상품 수정", description = "상품 정보를 수정합니다. 판매자 본인 또는 관리자가 수정할 수 있습니다.")
    @PutMapping("/{id}")
    public ApiResponseDto<ProductResponseDto> updateProduct(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long id,
        @RequestBody @Valid ProductCreateDto dto
    ) {
        User user = userDetails.getUser();
        ProductResponseDto response = productService.updateProduct(user, id, dto);
        return ApiResponseDto.success(response, "상품 수정 성공");
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다. 판매자 본인 또는 관리자가 삭제할 수 있습니다.")
    @DeleteMapping("/{id}")
    public ApiResponseDto<?> deleteProduct(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long id
    ) {
        User user = userDetails.getUser();
        productService.deleteProduct(user, id);
        return ApiResponseDto.success(null, "상품 삭제 성공");
    }

    @Operation(summary = "상품 상세 조회", description = "상품 ID를 통해 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ApiResponseDto<ProductResponseDto> getProduct(@PathVariable Long id) {
        ProductResponseDto response = productService.getProduct(id);
        return ApiResponseDto.success(response, "상품 조회 성공");
    }

    @GetMapping
    public ApiResponseDto<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return ApiResponseDto.success(products, "전체 상품 조회 성공");
    }
}
