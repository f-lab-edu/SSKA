package com.skka.domain.customer;

import static com.skka.adaptor.common.exception.ErrorType.INVALID_CUSTOMER_EMAIL;
import static com.skka.adaptor.common.exception.ErrorType.INVALID_CUSTOMER_NAME;
import static com.skka.adaptor.common.exception.ErrorType.INVALID_CUSTOMER_TEL;

import com.skka.adaptor.common.domain.BaseEntity;
import com.skka.adaptor.common.exception.ErrorType;
import com.skka.domain.customer.error.InvalidCustomerException;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String password;
    private String tel;

    private Customer(
        final long id,
        final String name,
        final String email,
        final String password,
        final String tel
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public static Customer of(
        final long id,
        final String name,
        final String email,
        final String password,
        final String tel
    ) {

        require(o -> name == null, name, INVALID_CUSTOMER_NAME);
        require(o -> email == null, email, INVALID_CUSTOMER_EMAIL);
        require(o -> tel == null, tel, INVALID_CUSTOMER_TEL);

        return new Customer(id, name, email, password, tel);
    }

    private static <T> void require(final Predicate<T> predicate, final T target, final ErrorType msg) {
        if (predicate.test(target)) {
            throw new InvalidCustomerException(msg);
        }
    }
}
