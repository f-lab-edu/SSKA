package com.skka.domain.studyseat.schedule.error;

import com.skka.adapter.common.exception.InvalidInputException;
import com.skka.adapter.common.exception.ErrorType;

public class InvalidScheduleException extends InvalidInputException {

    public InvalidScheduleException(ErrorType errorType) {
        super(errorType);
    }
}
