package com.tomandjerry.giantmall.common.dto;

public record ApiResponseDto<T>(boolean success, String message, T data) {
    public static <T> ApiResponseDto<T> success(T data, String message) {
        return new ApiResponseDto<>(true, message, data);
    }
    public static <T> ApiResponseDto<T> fail(String message) {
        return new ApiResponseDto<>(false, message, null);
    }
}