package com.propertymanagement.portal.service.impl;

import com.propertymanagement.portal.domain.*;
import com.propertymanagement.portal.dto.request.MakeOfferRequest;
import com.propertymanagement.portal.exception.InvalidInputException;
import com.propertymanagement.portal.exception.RecordNotFoundException;
import com.propertymanagement.portal.repository.*;
import com.propertymanagement.portal.service.PropertyService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    PropertyRespository propertyRespository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Page<Property> findPropertiesByCriteria(
            Double minPrice, Double maxPrice,
            ListingType listingType, Integer minBedRooms,
            Integer maxBedRooms, Double minBathRooms, Double maxBathRooms, PropertyType propertyType,
            String city, String state, Pageable pageable) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> query = builder.createQuery(Property.class);
        Root<Property> root = query.from(Property.class);
        List<Predicate> predicates = buildPredicates(builder, root, minPrice, maxPrice, listingType, minBedRooms,
                maxBedRooms, minBathRooms, maxBathRooms, propertyType, city, state);

        query.where(predicates.toArray(new Predicate[0]));
        query.select(root);

        List<Property> properties = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Property> countRoot = countQuery.from(Property.class);
        List<Predicate> countPredicates = buildPredicates(builder, countRoot, minPrice, maxPrice, listingType, minBedRooms,
                maxBedRooms, minBathRooms, maxBathRooms, propertyType, city, state);

        countQuery.where(countPredicates.toArray(new Predicate[0]));
        countQuery.select(builder.count(countRoot));

        Long totalItems = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(properties, pageable, totalItems);
    }

    private List<Predicate> buildPredicates(CriteriaBuilder builder, Root<Property> root, Double minPrice,
                                            Double maxPrice, ListingType listingType, Integer minBedRooms,
                                            Integer maxBedRooms, Double minBathRooms, Double maxBathRooms,
                                            PropertyType propertyType, String city, String state) {

        List<Predicate> predicates = new ArrayList<>();

        if (minPrice != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if (listingType != null) {
            predicates.add(builder.equal(root.get("listingType"), listingType));
        }
        if (minBedRooms != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("bedRooms"), minBedRooms));
        }
        if (maxBedRooms != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("bedRooms"), maxBedRooms));
        }
        if (minBathRooms != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("bathRooms"), minBathRooms));
        }
        if (maxBathRooms != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("bathRooms"), maxBathRooms));
        }
        if (propertyType != null) {
            predicates.add(builder.equal(root.get("propertyType"), propertyType));
        }
        if (city != null && !city.isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get("address").get("city")), "%" + city.toLowerCase() + "%"));
        }
        if (state != null && !state.isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get("address").get("state")), "%" + state.toLowerCase() + "%"));
        }
        return predicates;
    }

    @Override
    public Property getPropertyById(long id) {
        return propertyRespository.findById(id).get();
    }
    @Override
    public void saveProperty(Property property) {
        propertyRespository.save(property);
    }

    public List<Property> findAllProperties() {
        return propertyRespository.findAll();
    }


    @Override
    public Offer makeOffer(Long propertyId, MakeOfferRequest makeOfferRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));

        if (property.getStatus()== PropertyStatus.CONTINGENT){
            throw new InvalidInputException("Property is in Contingent status, you cannot make an offer for this property");
        }

        //Check if the user already made an offer for the property and is either pemding or accepted
        if (property.getOffers().stream().anyMatch(o -> o.getCustomer().getId().equals(customer.getId()) && (o.getOfferStatus().equals(OfferStatus.PENDING) || o.getOfferStatus().equals(OfferStatus.ACCEPTED)))) {
            throw new InvalidInputException("You have already made an offer for this property");
        }

        Offer offer = new Offer();

        offer.setOfferAmount(makeOfferRequest.getOfferAmount());
        offer.setOfferDate(LocalDateTime.now());
        offer.setOfferStatus(OfferStatus.PENDING);
        property.setStatus(PropertyStatus.PENDING);
        offer.setCustomer(customer);
        offer.setProperty(property);
        offer.setOfferType(makeOfferRequest.getOfferType());

        customer.addOffer(offer);
        property.addOffer(offer);
        offerRepository.save(offer);
        customerRepository.save(customer);
        propertyRepository.save(property);
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

        //Check if the user already made an offer for the property and is either rejected or accepted
        if (offer.getOfferStatus().equals(OfferStatus.REJECTED) || offer.getOfferStatus().equals(OfferStatus.ACCEPTED)) {
            throw new InvalidInputException("You cannot update an offer that has been rejected or accepted");
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
        // Check if the number of offers on property is greater than 1
        if (property.getOffers().size() > 1) {
            property.setStatus(PropertyStatus.PENDING);
        }else {
            property.setStatus(PropertyStatus.AVAILABLE);
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
        propertyRepository.save(property);
    }
    @Override
    public void rejectOffer(Long propertyId, Long offerId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new RecordNotFoundException("Offer not found with id: " + offerId));
        if (property.getStatus()== PropertyStatus.CONTINGENT){
            throw new InvalidInputException("Property is in Contingent status, you cannot reject an offer for this property");
        }
        if(property.getOffers().size()<2){
            property.setStatus(PropertyStatus.AVAILABLE);
        }
        offer.setOfferStatus(OfferStatus.REJECTED);
        propertyRepository.save(property);
    }

    @Override
    public void makeContingent(Long propertyId, Long offerId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new RecordNotFoundException("Offer not found with id: " + offerId));
        if (property.getStatus()== PropertyStatus.CONTINGENT){
            throw new InvalidInputException("Property is already in Contingent status");
        }
        if (property.getOffers().stream().anyMatch(o -> o.getOfferStatus().equals(OfferStatus.ACCEPTED))) {
            throw new InvalidInputException("Another offer has already been accepted for this property");
        }
        // Set the status of all the other offers to the property to rejected
        property.getOffers().stream().filter(o -> !o.getId().equals(offerId)).forEach(o -> o.setOfferStatus(OfferStatus.REJECTED));
        property.setStatus(PropertyStatus.CONTINGENT);
        propertyRepository.save(property);
    }

    @Override
    public void cancelContingent(Long propertyId, Long offerId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new RecordNotFoundException("Offer not found with id: " + offerId));
        if (property.getStatus()!= PropertyStatus.CONTINGENT){
            throw new InvalidInputException("Property is not in Contingent status");
        }
        if (property.getOffers().size()<2){
            property.setStatus(PropertyStatus.AVAILABLE);
        }
        propertyRepository.save(property);
    }

    @Override
    public Set<Property> getFavouritePropertiesByCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        return customer.getSavedList();
    }

    @Override
    public Set<Offer> getOffersByCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        return customer.getOffers();
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
