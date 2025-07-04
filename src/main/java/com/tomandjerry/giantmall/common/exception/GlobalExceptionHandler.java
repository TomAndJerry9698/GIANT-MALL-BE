package com.tomandjerry.giantmall.common.exception;

import com.tomandjerry.giantmall.common.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = {RestController.class}, basePackages = {"com.tomandjerry.giantmall.*"})
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode().getCode(), e.getMessage());
        return ResponseEntity
                .status(e.getStatus())
                .body(error);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException e) {
        String defaultMsg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponseDto error = new ErrorResponseDto("C001", defaultMsg);
        return ResponseEntity
                .badRequest()
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        ErrorResponseDto error = new ErrorResponseDto("C999", "서버 오류가 발생했습니다.");
        return ResponseEntity
                .internalServerError()
                .body(error);
    }
}
