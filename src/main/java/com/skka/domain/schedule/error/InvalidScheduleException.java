package com.skka.domain.schedule.error;

import com.skka.adaptor.common.exception.BadRequestException;
import com.skka.adaptor.common.exception.ErrorType;

public class InvalidScheduleException extends BadRequestException {

    public InvalidScheduleException(ErrorType errorType) {
        super(errorType);
    }
}
