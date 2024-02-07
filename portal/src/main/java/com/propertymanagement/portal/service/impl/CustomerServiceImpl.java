package com.propertymanagement.portal.service.impl;

import com.propertymanagement.portal.domain.*;
import com.propertymanagement.portal.dto.request.MakeOfferRequest;
import com.propertymanagement.portal.exception.InvalidInputException;
import com.propertymanagement.portal.exception.RecordNotFoundException;
import com.propertymanagement.portal.repository.CustomerRepository;
import com.propertymanagement.portal.repository.OfferRepository;
import com.propertymanagement.portal.repository.OwnerRepository;
import com.propertymanagement.portal.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OwnerRepository ownerRepository;


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


    @Override
    public Offer makeOffer(Long propertyId, MakeOfferRequest makeOfferRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));

        if (property.getStatus()== PropertyStatus.CONTINGENT){
            throw new InvalidInputException("Property is in Contingent status, you cannot make an offer for this property");
        }
        Offer offer = new Offer();
        System.out.println("Customer: " + customer);
        System.out.println("Authentication: " + authentication.getName());

        //Check if the user already made an offer for the property
        if (customer.getOffers().stream().anyMatch(o -> o.getProperty().getId().equals(propertyId))) {
            throw new InvalidInputException("You have already made an offer for this property");
        }

        offer.setOfferAmount(makeOfferRequest.getOfferAmount());
        offer.setOfferDate(LocalDateTime.now());
        offer.setOfferStatus(OfferStatus.PENDING);
        offer.setCustomer(customer);
        offer.setProperty(property);
        offer.setOfferType(makeOfferRequest.getOfferType());

        customer.addOffer(offer);
        property.addOffer(offer);
        offerRepository.save(offer);
        customerRepository.save(customer);
        return offer;
    }

    @Override
    public Offer updateOffer(Long propertyId, MakeOfferRequest makeOfferRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));
        if (property.getStatus()== PropertyStatus.CONTINGENT){
            throw new InvalidInputException("Property is in Contingent status, you cannot make an offer for this property");
        }
        Offer offer = offerRepository.findOfferByCustomerIdAndPropertyId(customer.getId(), propertyId);
        //Check if the user already made an offer for the property
        if (offer==null) {
            throw new InvalidInputException("You have not made an offer for this property");
        }

        offer.setOfferAmount(makeOfferRequest.getOfferAmount());
        offer.setOfferDate(LocalDateTime.now());
        offer.setOfferStatus(OfferStatus.PENDING);
        offer.setCustomer(customer);
        offer.setProperty(property);
        offer.setOfferType(makeOfferRequest.getOfferType());

        customer.addOffer(offer);
        property.addOffer(offer);
        offerRepository.save(offer);
        customerRepository.save(customer);
        return offer;
    }

    @Override
    public void deleteOffer(Long propertyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));
        if (property.getStatus()== PropertyStatus.CONTINGENT){
            property.setStatus(PropertyStatus.AVAILABLE);
        }
        Offer offer = offerRepository.findOfferByCustomerIdAndPropertyId(customer.getId(), propertyId);
        //Check if the user already made an offer for the property
        if (offer==null) {
            throw new InvalidInputException("You have not made an offer for this property");
        }
        customer.removeOffer(offer);
        property.removeOffer(offer);
        offerRepository.delete(offer);
    }

    @Override
    public void acceptOffer(Long propertyId, Long offerId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new RecordNotFoundException("Offer not found with id: " + offerId));
        if (property.getStatus()== PropertyStatus.CONTINGENT){
            throw new InvalidInputException("Property is in Contingent status, you cannot accept an offer for this property");
        }
        // Check if any other offer is accepted
        if (property.getOffers().stream().anyMatch(o -> o.getOfferStatus().equals(OfferStatus.ACCEPTED))) {
            throw new InvalidInputException("Another offer has already been accepted for this property");
        }

        offer.setOfferStatus(OfferStatus.ACCEPTED);
        property.setStatus(PropertyStatus.PENDING);
        propertyRepository.save(property);
    }
    @Override
    public void rejectOffer(Long propertyId, Long offerId) {
        /////////
    }

    @Override
    public void addToFavourites(Long propertyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));
        customer.addFavoutite(property);
        customerRepository.save(customer);
    }

    @Override
    public void removeFromFavourites(Long propertyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));
        customer.removeFavourite(property);
        customerRepository.save(customer);
    }




}
