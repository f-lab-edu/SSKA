package com.skka.domain.studyseat.schedule.error;

import com.skka.adapter.common.exception.BadRequestException;
import com.skka.adapter.common.exception.ErrorType;

public class InvalidScheduleException extends BadRequestException {

    public InvalidScheduleException(ErrorType errorType) {
        super(errorType);
    }
}
