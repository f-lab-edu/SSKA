package com.skka.domain.customer.repository;

import com.skka.domain.customer.Customer;

public interface CustomerRepository {

    public Customer save(Customer customer);

    public Customer findById(long id);
}
