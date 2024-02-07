package com.propertymanagement.portal.service;

import com.propertymanagement.portal.domain.Customer;
import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.dto.request.MakeOfferRequest;

import java.util.List;
import java.util.Set;

public interface CustomerService {
    public Customer getCustomerById(Long id);

    public void deleteCustomerById(Long id);
    public List<Customer> getAllCustomers();
    public Set<Property> getFavouritePropertiesByCustomer(Long id);
    public Set<Offer> getOffersByCustomer(Long id);

}
