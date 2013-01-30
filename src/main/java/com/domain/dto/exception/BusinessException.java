package com.domain.dto.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException() {
        super("Ocorreu um erro no processo");
    }
}
