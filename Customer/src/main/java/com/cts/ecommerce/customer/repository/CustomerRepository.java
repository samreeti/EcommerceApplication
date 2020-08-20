package com.cts.ecommerce.customer.repository;

import com.cts.ecommerce.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends JpaRepository<CustomerEntity, Integer> {
    public CustomerEntity findByCustomerName(String name);
}
