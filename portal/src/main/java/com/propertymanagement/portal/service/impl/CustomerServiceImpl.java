package com.propertymanagement.portal.service.impl;

import com.propertymanagement.portal.domain.*;
import com.propertymanagement.portal.repository.CustomerRepository;
import com.propertymanagement.portal.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            return customer.get();
        }else{
            return null;
        }
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Set<Property> getFavouritePropertiesByCustomer(Long id) {
        Customer customer = getCustomerById(id);
        if (customer!=null){
            return customer.getSavedList();
        }
        return null;
    }

    @Override
    public Set<Offer> getOffersByCustomer(Long id) {
        Customer customer = getCustomerById(id);
        if (customer!=null){
            return customer.getOffers();
        }
        return null;
    }
}
