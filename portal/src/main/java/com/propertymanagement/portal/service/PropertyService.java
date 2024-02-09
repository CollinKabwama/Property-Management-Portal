package com.propertymanagement.portal.service;

import com.propertymanagement.portal.domain.ListingType;
import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.domain.PropertyType;
import com.propertymanagement.portal.dto.OfferDTO;
import com.propertymanagement.portal.dto.PropertyDTO;
import com.propertymanagement.portal.dto.request.MakeOfferRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface PropertyService {
    public void saveProperty(Property property);

    PropertyDTO getPropertyById(Long id);

    public Set<PropertyDTO> getAllProperties();

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


    PropertyDTO createProperty(PropertyDTO propertyDTO);

   PropertyDTO addProperty(PropertyDTO propertyDTO);

    public Set <PropertyDTO> addProperties(Set<PropertyDTO> propertyDTOs);

   boolean removeProperty( Long propertyId);

    PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO);

    public Set <OfferDTO> getAllOffers();

   public Set <OfferDTO> getOffersByPropertyId(Long propertyId);

   public Set <PropertyDTO> getAllPropertiesByOwner();

    public PropertyDTO convertToDTO(Property property);

    public Property convertToEntity(PropertyDTO propertyDTO);



    void makeContingent(Long propertyId, Long offerId);

    void cancelContingent(Long propertyId, Long offerId);

    Set<Property> getFavouritePropertiesByCustomer();

    Set<OfferDTO> getOffersByCustomer();
    public boolean illegibilityToMakeOffer(Long propertyId) ;
    public boolean canDeleteOffer(Long propertyId) ;
    public List<OfferDTO> offersByOwner();
    public boolean canCancelContingent(Long propertyId, Long offerId) ;
    public Set<Property> getPropertiesBelongingToOwner() ;




    }
