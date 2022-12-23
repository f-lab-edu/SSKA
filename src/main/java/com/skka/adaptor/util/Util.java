package com.skka.adaptor.util;

import com.skka.adaptor.common.exception.ErrorType;
import com.skka.domain.customer.error.InvalidCustomerException;
import com.skka.domain.schedule.error.InvalidScheduleException;
import com.skka.domain.studyseat.error.InvalidStudySeatException;
import java.util.function.Predicate;

public class Util {

    public static <T> void requireCustomer(final Predicate<T> predicate, final T target, final ErrorType msg) {
        if (predicate.test(target)) {
            throw new InvalidCustomerException(msg);
        }
    }

    public static <T> void requireStudySeat(final Predicate<T> predicate, final T target, final ErrorType msg) {
        if (predicate.test(target)) {
            throw new InvalidStudySeatException(msg);
        }
    }

    public static <T> void requireSchedule(final Predicate<T> predicate, final T target, final ErrorType msg) {
        if (predicate.test(target)) {
            throw new InvalidScheduleException(msg);
        }
    }
}
