package com.tomandjerry.giantmall.cart.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;

@Getter
public class CartDeleteRequestDto {

    @NotEmpty(message = "삭제할 장바구니 ID 목록은 비어있을 수 없습니다.")
    private List<Long> cartIds;
}
