package com.skka.adapter.outbound.jpa.customer;

import static com.skka.adapter.util.EntityConverter.toCustomerEntity;

import com.skka.domain.customer.Customer;
import com.skka.domain.customer.repository.CustomerRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@Getter
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = toCustomerEntity(customer);
        jpaRepository.save(entity);
        return entity.toCustomerReturn();
    }

    @Override
    public Customer findById(long id) {
        CustomerEntity foundEntity = jpaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("고객을 찾지 못했습니다."));
        return foundEntity.toCustomerReturn();
    }
}
