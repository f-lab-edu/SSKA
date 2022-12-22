package com.skka.domain.customer;

import static com.skka.adaptor.common.exception.ErrorType.INVALID_CUSTOMER_EMAIL;
import static com.skka.adaptor.common.exception.ErrorType.INVALID_CUSTOMER_NAME;
import static com.skka.adaptor.common.exception.ErrorType.INVALID_CUSTOMER_TEL;
import static com.skka.adaptor.util.Util.requireCustomer;

import com.skka.adaptor.common.domain.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "customer")
@Getter
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String password;
    private String tel;

    protected Customer() {
        super(LocalDateTime.now(), LocalDateTime.now());
    }

    public Customer(
        final long id,
        final String name,
        final String email,
        final String password,
        final String tel
    ) {
        super(LocalDateTime.now(), LocalDateTime.now());

        requireCustomer(o -> name == null, name, INVALID_CUSTOMER_NAME);
        requireCustomer(o -> email == null, email, INVALID_CUSTOMER_EMAIL);
        requireCustomer(o -> tel == null, tel, INVALID_CUSTOMER_TEL);

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }
}
