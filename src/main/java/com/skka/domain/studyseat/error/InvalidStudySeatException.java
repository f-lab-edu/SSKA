package com.skka.domain.studyseat.error;

import com.skka.adapter.common.exception.InvalidInputException;
import com.skka.adapter.common.exception.ErrorType;

public class InvalidStudySeatException extends InvalidInputException {

    public InvalidStudySeatException(ErrorType errorType) {
        super(errorType);
    }
}
