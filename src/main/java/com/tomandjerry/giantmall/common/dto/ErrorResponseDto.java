package com.tomandjerry.giantmall.common.dto;

public record ErrorResponseDto(
        String errorCode,
        String errorMessage
) {}