package com.skka.customer;

import com.skka.domain.customer.Customer;
import java.time.LocalDateTime;

public class CustomerFixture {

    public static Customer CUSTOMER = Customer.of(
        1L,
        "용용이",
        "yongyong@naver.com",
        "aaaaaaa",
        "010-1231-1727",
        LocalDateTime.of(2023, 1, 10, 12, 10),
        LocalDateTime.of(2023, 1, 10, 15, 10)
    );
}
