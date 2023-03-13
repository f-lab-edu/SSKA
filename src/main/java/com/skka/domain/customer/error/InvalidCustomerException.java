package com.skka.domain.customer.error;

import com.skka.adapter.common.exception.InvalidInputException;
import com.skka.adapter.common.exception.ErrorType;

public class InvalidCustomerException extends InvalidInputException {

    public InvalidCustomerException(ErrorType errorType) {
        super(errorType);
    }
}
