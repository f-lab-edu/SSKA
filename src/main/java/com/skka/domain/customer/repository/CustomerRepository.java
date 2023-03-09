package com.skka.domain.customer.repository;

import com.skka.domain.customer.Customer;
import java.util.Optional;

public interface CustomerRepository {

    public Customer save(Customer customer);

    public Optional<Customer> findById(long id);
}
