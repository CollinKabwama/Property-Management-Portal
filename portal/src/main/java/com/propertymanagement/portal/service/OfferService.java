package com.propertymanagement.portal.service;

import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.dto.OfferDTO;

import java.util.List;
import java.util.Set;

public interface OfferService {
    public Set<OfferDTO> getAllOffers();
}
