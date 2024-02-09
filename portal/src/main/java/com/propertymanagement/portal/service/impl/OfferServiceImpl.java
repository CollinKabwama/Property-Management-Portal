package com.propertymanagement.portal.service.impl;

import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.dto.OfferDTO;
import com.propertymanagement.portal.repository.OfferRepository;
import com.propertymanagement.portal.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<OfferDTO> getAllOffers() {
        return offerRepository.findAll().stream()
                .map(offer -> modelMapper.map(offer, OfferDTO.class))
                .collect(Collectors.toSet());
    }
}
