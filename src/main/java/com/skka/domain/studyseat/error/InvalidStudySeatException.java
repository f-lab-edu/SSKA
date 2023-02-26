package com.skka.domain.studyseat.error;

import com.skka.adapter.common.exception.BadRequestException;
import com.skka.adapter.common.exception.ErrorType;

public class InvalidStudySeatException extends BadRequestException {

    public InvalidStudySeatException(ErrorType errorType) {
        super(errorType);
    }
}
