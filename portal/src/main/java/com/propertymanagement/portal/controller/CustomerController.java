package com.propertymanagement.portal.controller;


import com.propertymanagement.portal.domain.Customer;
import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.dto.request.MakeOfferRequest;
import com.propertymanagement.portal.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }
    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/favourite-properties")
    public Set<Property> getFavouritePropertiesByCustomer(@PathVariable Long id){
        return customerService.getFavouritePropertiesByCustomer(id);
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/offers")
    public Set<Offer> getOffersByCustomer(Long id){
        return customerService.getOffersByCustomer(id);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/properties/{propertyId}/offers")
    public Offer makeOffer(@PathVariable Long propertyId, @RequestBody MakeOfferRequest makeOfferRequest){
        return customerService.makeOffer(propertyId, makeOfferRequest);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/properties/{propertyId}/like")
    public void addToFavourites(@PathVariable Long propertyId){
        customerService.addToFavourites(propertyId);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/properties/{propertyId}/unlike")
    public void removeFromFavourites(@PathVariable Long propertyId){
        customerService.removeFromFavourites(propertyId);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/properties/{propertyId}/offers")
    public Offer updateOffer(@PathVariable Long propertyId, @RequestBody MakeOfferRequest makeOfferRequest){
        return customerService.updateOffer(propertyId, makeOfferRequest);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/properties/{propertyId}/offers")
    public void deleteOffer(@PathVariable Long propertyId){
        customerService.deleteOffer(propertyId);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/properties/{propertyId}/offers/{offerId}/accept")
    public void acceptOffer(@PathVariable Long propertyId, @PathVariable Long offerId){
        customerService.acceptOffer(propertyId, offerId);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/properties/{propertyId}/offers/{offerId}/reject")
    public void rejectOffer(@PathVariable Long propertyId, @PathVariable Long offerId){
        customerService.rejectOffer(propertyId, offerId);
    }







}
