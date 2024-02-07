package com.propertymanagement.portal.controller;

import com.propertymanagement.portal.domain.ListingType;
import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.domain.PropertyType;
import com.propertymanagement.portal.dto.request.MakeOfferRequest;
import com.propertymanagement.portal.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/properties")
public class PropertyController {

    @Autowired
    PropertyService propertyService;
    public void saveProperty(Property property){
        propertyService.saveProperty(property);
    }

    @GetMapping("/")
    public List<Property> findAllProperties(){
        return propertyService.findAllProperties();
    }


    @GetMapping("/{id}")
    public Property getPropertyById(@PathVariable("id") long id) {
        return propertyService.getPropertyById(id);
    }
    @GetMapping
    public Page<Property> getProperties(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) ListingType listingType,
            @RequestParam(required = false) Integer minBedRooms,
            @RequestParam(required = false) Integer maxBedRooms,
            @RequestParam(required = false) Double minBathRooms,
            @RequestParam(required = false) Double maxBathRooms,
            @RequestParam(required = false) PropertyType propertyType,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return propertyService.findPropertiesByCriteria(minPrice, maxPrice, listingType, minBedRooms, maxBedRooms,
                minBathRooms, maxBathRooms, propertyType, city, state, pageable);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{propertyId}/offers")
    public Offer makeOffer(@PathVariable Long propertyId, @RequestBody MakeOfferRequest makeOfferRequest){
        return propertyService.makeOffer(propertyId, makeOfferRequest);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{propertyId}/like")
    public void addToFavourites(@PathVariable Long propertyId){
        propertyService.addToFavourites(propertyId);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{propertyId}/unlike")
    public void removeFromFavourites(@PathVariable Long propertyId){
        propertyService.removeFromFavourites(propertyId);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{propertyId}/offers")
    public Offer updateOffer(@PathVariable Long propertyId, @RequestBody MakeOfferRequest makeOfferRequest){
        return propertyService.updateOffer(propertyId, makeOfferRequest);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{propertyId}/offers")
    public void deleteOffer(@PathVariable Long propertyId){
        propertyService.deleteOffer(propertyId);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{propertyId}/offers/{offerId}/accept")
    public void acceptOffer(@PathVariable Long propertyId, @PathVariable Long offerId){
        propertyService.acceptOffer(propertyId, offerId);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{propertyId}/offers/{offerId}/reject")
    public void rejectOffer(@PathVariable Long propertyId, @PathVariable Long offerId){
        propertyService.rejectOffer(propertyId, offerId);
    }







}
