package com.propertymanagement.portal.repository;

import com.propertymanagement.portal.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByUserEmail(String email);
}
