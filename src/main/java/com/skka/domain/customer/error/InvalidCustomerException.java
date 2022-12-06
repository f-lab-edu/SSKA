package com.skka.domain.customer.error;

import com.skka.adaptor.common.exception.BadRequestException;
import com.skka.adaptor.common.exception.ErrorType;

public class InvalidCustomerException extends BadRequestException {

    public InvalidCustomerException(ErrorType errorType) {
        super(errorType);
    }
}
