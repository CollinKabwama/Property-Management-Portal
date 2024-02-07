package com.propertymanagement.portal.repository;

import com.propertymanagement.portal.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Offer findOfferByCustomerIdAndPropertyId(Long customerId, Long propertyId);
}
