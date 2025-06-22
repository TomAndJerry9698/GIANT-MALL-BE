package com.tomandjerry.giantmall.common.dto;

import lombok.Builder;

@Builder
public record ApiResponseDto<T>(boolean success, String message, T data) {
    public static <T> ApiResponseDto<T> success(T data, String message) {
        return ApiResponseDto.<T>builder().success(true).message(message).data(data).build();
    }

    public static <T> ApiResponseDto<T> fail(String message) {
        return ApiResponseDto.<T>builder().success(false).message(message).data(null).build();
    }
}
