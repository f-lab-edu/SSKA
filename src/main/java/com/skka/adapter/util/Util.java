package com.skka.adapter.util;

import com.skka.adapter.common.exception.BadRequestException;
import com.skka.adapter.common.exception.ErrorType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Predicate;

public class Util {

    public static <T> void require(final Predicate<T> predicate, final T target, final ErrorType errorType) {
        if (predicate.test(target)) {
            throw new BadRequestException(errorType);
        }
    }

    public static void check(final boolean condition, final ErrorType errorType) {
        if (condition) {
            throw new BadRequestException(errorType);
        }
    }

    public static long checkTimeDifference(
        final LocalDateTime startedTime,
        final LocalDateTime endTime
    ) {
        Duration diff = Duration.between(startedTime, endTime);
        return diff.toHours();
    }
}
