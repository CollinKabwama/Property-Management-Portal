package com.propertymanagement.portal.repository;

import com.propertymanagement.portal.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByUserEmail(String email);
}
