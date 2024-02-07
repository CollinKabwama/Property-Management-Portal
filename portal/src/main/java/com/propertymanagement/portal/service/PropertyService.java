package com.propertymanagement.portal.service;

import com.propertymanagement.portal.domain.ListingType;
import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.domain.PropertyType;
import com.propertymanagement.portal.dto.request.MakeOfferRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PropertyService {
    public void saveProperty(Property property);

    Property getPropertyById(long id);

    List<Property> findAllProperties();

    public Page<Property> findPropertiesByCriteria(
            Double minPrice, Double maxPrice,
            ListingType listingType, Integer minBedRooms,
            Integer maxBedRooms, Double minBathRooms, Double maxBathRooms, PropertyType propertyType,
            String city, String state, Pageable pageable);

    Offer makeOffer(Long propertyId, MakeOfferRequest makeOfferRequest);

    void removeFromFavourites(Long propertyId);

    void addToFavourites(Long propertyId);

    Offer updateOffer(Long propertyId, MakeOfferRequest makeOfferRequest);

    void deleteOffer(Long propertyId);

    void acceptOffer(Long propertyId, Long offerId);

    void rejectOffer(Long propertyId, Long offerId);
}
