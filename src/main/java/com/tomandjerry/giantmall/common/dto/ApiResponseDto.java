package com.tomandjerry.giantmall.common.dto;

public record ApiResponseDto<T>(String message, T data) {

    public static <T> ApiResponseDto<T> success(T data, String message) {
        return new ApiResponseDto<>(message, data);
    }
}