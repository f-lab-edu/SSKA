package com.skka.domain.customer;

import static com.skka.adapter.common.exception.ErrorType.INVALID_CUSTOMER_EMAIL;
import static com.skka.adapter.common.exception.ErrorType.INVALID_CUSTOMER_NAME;
import static com.skka.adapter.common.exception.ErrorType.INVALID_CUSTOMER_TEL;
import static com.skka.adapter.util.Util.require;

import com.skka.domain.studyseat.schedule.Schedule;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Customer {

    private long id;
    private String name;
    private String email;
    private String password;
    private String tel;
    private List<Schedule> schedules = new ArrayList<>();
    private LocalDateTime lastModified;
    private LocalDateTime createdAt;

    private Customer(
        final long id,
        final String name,
        final String email,
        final String password,
        final String tel,
        final LocalDateTime lastModified,
        final LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.lastModified = lastModified;
        this.createdAt = createdAt;
    }

    public static Customer of(
        final long id,
        final String name,
        final String email,
        final String password,
        final String tel,
        final LocalDateTime lastModified,
        final LocalDateTime createdAt
    ) {

        require(o -> name == null, name, INVALID_CUSTOMER_NAME);
        require(o -> email == null, email, INVALID_CUSTOMER_EMAIL);
        require(o -> tel == null, tel, INVALID_CUSTOMER_TEL);

        return new Customer(id, name, email, password, tel, lastModified, createdAt);
    }
}
