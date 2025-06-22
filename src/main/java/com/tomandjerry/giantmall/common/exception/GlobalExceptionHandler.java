package com.tomandjerry.giantmall.common.exception;

import com.tomandjerry.giantmall.common.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponseDto<?>> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ApiResponseDto.fail(e.getMessage()));
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<?>> handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity
                .badRequest()
                .body(ApiResponseDto.fail(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<?>> handleException(Exception e) {
        return ResponseEntity
                .internalServerError()
                .body(ApiResponseDto.fail("서버 오류가 발생했습니다."));
    }
}
