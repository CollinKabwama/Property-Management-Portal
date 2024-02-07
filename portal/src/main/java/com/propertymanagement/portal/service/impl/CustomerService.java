package com.propertymanagement.portal.service.impl;

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
    Offer makeOffer(Long propertyId, MakeOfferRequest makeOfferRequest);
    void addToFavourites(Long propertyId);

    void removeFromFavourites(Long propertyId);
    public Offer updateOffer(Long propertyId, MakeOfferRequest makeOfferRequest) ;

    void deleteOffer(Long propertyId);

    void acceptOffer(Long propertyId, Long offerId);

    void rejectOffer(Long propertyId, Long offerId);
}
