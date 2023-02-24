package com.skka.domain.customer;

import static com.skka.customer.CustomerFixture.CUSTOMER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.skka.adapter.common.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Customer 는 ")
class CustomerTest {

    @DisplayName("생성된다")
    @Test
    void test1() {
        assertDoesNotThrow(() -> Customer.of (
            CUSTOMER.getId(),
            CUSTOMER.getName(),
            CUSTOMER.getEmail(),
            CUSTOMER.getPassword(),
            CUSTOMER.getTel(),
            CUSTOMER.getLastModified(),
            CUSTOMER.getCreatedAt()
        ));
    }

    @DisplayName("필수값 이름이 없을 때 생성 되지 않는다")
    @Test
    void test2() {
        assertThatThrownBy(() -> Customer.of (
            CUSTOMER.getId(),
            null,
            CUSTOMER.getEmail(),
            CUSTOMER.getPassword(),
            CUSTOMER.getTel(),
            CUSTOMER.getLastModified(),
            CUSTOMER.getCreatedAt()
        ))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("올바르지 않은 이름입니다.");
    }

    @DisplayName("필수값 이메일이 없을 때 생성 되지 않는다")
    @Test
    void test3() {
        assertThatThrownBy(() -> Customer.of (
            CUSTOMER.getId(),
            CUSTOMER.getName(),
            null,
            CUSTOMER.getPassword(),
            CUSTOMER.getTel(),
            CUSTOMER.getLastModified(),
            CUSTOMER.getCreatedAt()
        ))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("올바르지 않은 이메일입니다.");
    }

    @DisplayName("필수값 전화번호가 없을 때 생성 되지 않는다")
    @Test
    void test4() {
        assertThatThrownBy(() -> Customer.of (
            CUSTOMER.getId(),
            CUSTOMER.getName(),
            CUSTOMER.getEmail(),
            CUSTOMER.getPassword(),
            null,
            CUSTOMER.getLastModified(),
            CUSTOMER.getCreatedAt()
        ))
            .isInstanceOf(BadRequestException.class)
            .hasMessage("올바르지 않은 전화번호입니다.");
    }
}
