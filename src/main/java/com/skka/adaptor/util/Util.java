package com.skka.adaptor.util;

import com.skka.adaptor.common.exception.ErrorType;

import java.util.function.Predicate;

public class Util {

    public static <T> void require(final Predicate<T> predicate, final T target,
        final ErrorType msg) {
        if (predicate.test(target)) {
            throw new IllegalArgumentException(msg.getMessage());
        }
    }
}
