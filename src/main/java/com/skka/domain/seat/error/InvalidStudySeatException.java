package com.skka.domain.seat.error;

import com.skka.adaptor.common.exception.BadRequestException;
import com.skka.adaptor.common.exception.ErrorType;

public class InvalidStudySeatException extends BadRequestException {

    public InvalidStudySeatException(ErrorType errorType) {
        super(errorType);
    }
}
