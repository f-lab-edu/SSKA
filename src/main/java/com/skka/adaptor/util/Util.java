package com.skka.adaptor.util;

import com.skka.adaptor.common.exception.ErrorType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Predicate;

public class Util {

    public static <T> void require(final Predicate<T> predicate, final T target,
        final ErrorType msg) {
        if (predicate.test(target)) {
            throw new IllegalArgumentException(msg.getMessage());
        }
    }

    public static void check(final boolean condition, final ErrorType msg) {
        if (condition) {
            throw new IllegalStateException(msg.getMessage());
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
