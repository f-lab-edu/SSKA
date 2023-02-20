package com.skka.domain.customer.error;

import com.skka.adapter.common.exception.BadRequestException;
import com.skka.adapter.common.exception.ErrorType;

public class InvalidCustomerException extends BadRequestException {

    public InvalidCustomerException(ErrorType errorType) {
        super(errorType);
    }
}
