package com.skka.adaptor.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final int code;

    public BadRequestException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }
}
