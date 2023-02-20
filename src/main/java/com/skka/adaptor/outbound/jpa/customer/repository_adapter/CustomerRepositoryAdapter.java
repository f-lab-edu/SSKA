package com.skka.adaptor.outbound.jpa.customer.repository_adapter;

import com.skka.adaptor.outbound.jpa.customer.CustomerEntity;
import com.skka.adaptor.outbound.jpa.customer.repository.CustomerJpaRepository;
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
        CustomerEntity entity = customer.toCustomerEntity();
        System.out.println("88 = " + entity);
        jpaRepository.save(entity);
        return entity.toCustomer();
    }

    @Override
    public Customer findById(long id) {
        CustomerEntity foundEntity = jpaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("고객을 찾지 못했습니다."));
        System.out.println("77 = " + foundEntity);
        return foundEntity.toCustomer();
    }
}
