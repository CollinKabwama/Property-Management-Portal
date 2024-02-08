package com.propertymanagement.portal.service.impl;

import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.repository.OfferRepository;
import com.propertymanagement.portal.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }
}
