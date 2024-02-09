package com.propertymanagement.portal.service.impl;

import com.propertymanagement.portal.domain.*;
import com.propertymanagement.portal.dto.AddressDTO;
import com.propertymanagement.portal.dto.OfferDTO;
import com.propertymanagement.portal.dto.OwnerDTO;
import com.propertymanagement.portal.dto.PropertyDTO;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
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
    private ModelMapper modelMapper;

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
    public PropertyDTO getPropertyById(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Property is not found"));
        return convertToDTO(property);

    }

    @Override
   public void saveProperty(Property property) {
        propertyRespository.save(property);
    }

    public Set<PropertyDTO> getAllProperties() {
        Set<Property> properties = new HashSet<>(propertyRespository.findAll());

        return properties.stream()
                .map(property -> modelMapper.map(property, PropertyDTO.class))
                .collect(Collectors.toSet());

    }

    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner owner  = ownerRepository.findOwnerByUserEmail(authentication.getName());
        Property property = convertToEntity(propertyDTO);
        property.setOwner(owner);
        AddressDTO addressDTO = propertyDTO.getAddress();
        Address address = convertToAddress(addressDTO);
        property.setAddress(address);
        property = propertyRespository.save(property);
        return convertToDTO(property);

    }

    private Address convertToAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setCity(addressDTO.getLine1());
        address.setLine1(addressDTO.getLine2());
        address.setLine2(addressDTO.getCity());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        return address;
    }

    public PropertyDTO addProperty( PropertyDTO propertyDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner owner  = ownerRepository.findOwnerByUserEmail(authentication.getName());
        if (owner == null) {
            throw new NoSuchElementException ("Owner not found or not authenticated.");
        }
        Property property = modelMapper.map(propertyDTO, Property.class);
        property.setOwner(owner);
        Property savedProperty = propertyRepository.save(property);
        return modelMapper.map(savedProperty, PropertyDTO.class);

    }


    public PropertyDTO updateProperty( Long propertyId, PropertyDTO propertyDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner owner  = ownerRepository.findOwnerByUserEmail(authentication.getName());
        if (owner == null) {
            throw new NoSuchElementException ("Owner not found or not authenticated.");
        }

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new NoSuchElementException("Property not found or not accessible by owner"));

        if (!PropertyStatus.AVAILABLE.equals(property.getStatus())) {
            throw new IllegalStateException("Cannot update property while it is under Pending or Contingent status");
        }
        property.setName(propertyDTO.getName());
        property.setDescription(propertyDTO.getDescription());
        property.setPrice(propertyDTO.getPrice());
        property.setBathRooms(propertyDTO.getBathRooms());
        property.setBedRooms(propertyDTO.getBedRooms());

        AddressDTO addressDTO = propertyDTO.getAddress();
        Address address = convertToAddress(addressDTO);
        property.setAddress(address);
        property.setImageUrl(propertyDTO.getImageUrl());
        property.setConstructionDate(propertyDTO.getConstructionDate());
        property.setStatus(propertyDTO.getStatus());
        property.setOwner(owner);
        Property updatedProperty = propertyRepository.save(property);
        return modelMapper.map(updatedProperty, PropertyDTO.class);
    }

    public boolean removeProperty( Long propertyId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner owner  = ownerRepository.findOwnerByUserEmail(authentication.getName());
        if (owner == null) {
            throw new NoSuchElementException ("Owner not found or not authenticated.");
        }
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new NoSuchElementException("Property not found"));

        if(!PropertyStatus.AVAILABLE.equals(property.getStatus())){
            throw new IllegalArgumentException("Cannot remove property that is under Pending or Contingent status");
        }
        owner.getProperties().remove(property);
        propertyRepository.delete(property);
        return true;

    }

    public Set<PropertyDTO> getAllPropertiesByOwner() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner owner  = ownerRepository.findOwnerByUserEmail(authentication.getName());
        if (owner == null) {
            throw new NoSuchElementException ("Owner not found or not authenticated.");
        }
        return owner.getProperties().stream()
                .map(property -> modelMapper.map(property, PropertyDTO.class))
                .collect(Collectors.toSet());
    }

    public Set<OfferDTO> getOffersByPropertyId(Long propertyId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner owner  = ownerRepository.findOwnerByUserEmail(authentication.getName());
        if (owner == null) {
            throw new NoSuchElementException ("Owner not found or not authenticated.");
        }
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new NoSuchElementException("Property not found"));

        return property.getOffers().stream()
                .map(offer -> modelMapper.map(offer, OfferDTO.class))
                .collect(Collectors.toSet());

    }


    public Set <OfferDTO> getAllOffers() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner owner  = ownerRepository.findOwnerByUserEmail(authentication.getName());
        if (owner == null) {
            throw new NoSuchElementException ("Owner not found or not authenticated.");
        }

        Set <Offer> offers = new HashSet<>(offerRepository.findAll());

        return offers.stream().map (offer -> modelMapper.map(offer, OfferDTO.class))
                .collect(Collectors.toSet());

    }

    public Set<PropertyDTO> addProperties(Set<PropertyDTO> propertyDTOs) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner owner  = ownerRepository.findOwnerByUserEmail(authentication.getName());
        if (owner == null) {
            throw new NoSuchElementException ("Owner not found or not authenticated.");
        }

        Set<Property> properties = new HashSet<>();
        for (PropertyDTO propertyDTO : propertyDTOs) {
            Property property = convertToEntity(propertyDTO);
            property.setOwner(owner);
            properties.add(property);
        }
        propertyRespository.saveAll(properties);

        return propertyDTOs;

    }





    public PropertyDTO convertToDTO(Property property) {
        return modelMapper.map(property, PropertyDTO.class);
    }

    public Property convertToEntity(PropertyDTO propertyDTO) {
        return modelMapper.map(propertyDTO, Property.class);
    }

    @Override
    public boolean illegibilityToMakeOffer(Long propertyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));
        if (property.getStatus()== PropertyStatus.CONTINGENT){
            return false;
        }
        //Check if the user already made an offer for the property and is either pemding or accepted
        if (property.getOffers().stream().anyMatch(o -> o.getCustomer().getId().equals(customer.getId()) && (o.getOfferStatus().equals(OfferStatus.PENDING) || o.getOfferStatus().equals(OfferStatus.ACCEPTED)))) {
            return false;
        }
        return true;
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
            throw new InvalidInputException("Property is in Contingent status, you cannot cancel an offer for this property");
        }

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
    public boolean canDeleteOffer(Long propertyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));
        if (property.getStatus()== PropertyStatus.CONTINGENT){
            return false;
        }
        return true;
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
        offerRepository.save(offer);
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
        if (property.getStatus() == PropertyStatus.CONTINGENT){
            throw new InvalidInputException("Property is in Contingent status, Can't be cancelled");
        }
        // Return the size of the offers to a property that aren't in rejected status
        if (property.getOffers().stream().filter(o -> !o.getOfferStatus().equals(OfferStatus.REJECTED)).count() > 2) {
            property.setStatus(PropertyStatus.PENDING);
        }

        offer.setOfferStatus(OfferStatus.REJECTED);
        propertyRepository.save(property);
    }

    @Override
    public boolean canCancelContingent(Long propertyId, Long offerId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RecordNotFoundException("Property not found with id: " + propertyId));
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new RecordNotFoundException("Offer not found with id: " + offerId));
        if (property.getStatus() != PropertyStatus.CONTINGENT){
            //Check if property already has an accepted offer
            if (property.getOffers().stream().anyMatch(o -> o.getOfferStatus().equals(OfferStatus.ACCEPTED))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Property> getPropertiesBelongingToOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner owner  = ownerRepository.findOwnerByUserEmail(authentication.getName());
        return owner.getProperties();
    }


    @Override
    public Set<Property> getFavouritePropertiesByCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        return customer.getSavedList();
    }

    @Override
    public Set<OfferDTO> getOffersByCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findCustomerByUserEmail(authentication.getName());
        return customer.getOffers().stream().map (offer -> modelMapper.map(offer, OfferDTO.class))
                .collect(Collectors.toSet());
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

    @Override
    public List<OfferDTO> offersByOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner owner  = ownerRepository.findOwnerByUserEmail(authentication.getName());
        return owner.getProperties().stream().flatMap(p -> p.getOffers().stream()).map(o -> modelMapper.map(o, OfferDTO.class)).collect(Collectors.toList());
    }


}
