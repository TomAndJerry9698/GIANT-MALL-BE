package com.tomandjerry.giantmall.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    FORBIDDEN("A001", "접근 권한이 없는 사용자입니다.", HttpStatus.FORBIDDEN),
    VALIDATION_ERROR("C001", "잘못된 요청입니다. 입력값을 확인해주세요.", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("C999", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_NOT_FOUND("P001", "존재하지 않는 상품입니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
}