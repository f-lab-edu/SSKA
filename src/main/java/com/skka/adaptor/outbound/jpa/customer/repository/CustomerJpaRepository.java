package com.skka.adaptor.outbound.jpa.customer.repository;

import com.skka.adaptor.outbound.jpa.customer.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

}
