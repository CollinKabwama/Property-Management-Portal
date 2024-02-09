package com.propertymanagement.portal.repository;

import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.dto.OfferDTO;
import com.propertymanagement.portal.dto.PropertyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface PropertyRepository extends JpaRepository<Property, Long> {


    @Query("SELECT p.offers FROM Property p WHERE p.id = :propertyId")
    Set<OfferDTO> getOffersByPropertyId();

    @Query("SELECT p FROM Property p WHERE p.owner.id = :ownerId")
    Set <PropertyDTO> getPropertiesByOwnerId(Long ownerId);

    @Query("SELECT p FROM Property p WHERE p.owner.id = :ownerId")
    Page<Property> getPropertiesByOwnerId(Long ownerId, Pageable pageable);




}
