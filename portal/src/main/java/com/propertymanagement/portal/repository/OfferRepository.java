package com.propertymanagement.portal.repository;

import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.dto.OfferDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Offer findOfferByCustomerIdAndPropertyId(Long customerId, Long propertyId);



}
