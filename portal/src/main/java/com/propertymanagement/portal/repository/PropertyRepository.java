package com.propertymanagement.portal.repository;

import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.dto.OfferDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface PropertyRepository extends JpaRepository<Property, Long> {


    @Query("SELECT p.offers FROM Property p WHERE p.id = :propertyId")
    Set<OfferDTO> getOffersByPropertyId();
}
