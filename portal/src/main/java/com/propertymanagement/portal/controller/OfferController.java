package com.propertymanagement.portal.controller;

import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.dto.OfferDTO;
import com.propertymanagement.portal.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {
    @Autowired
    private OfferService offerService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public Set<OfferDTO> getAllOffers(){
        return offerService.getAllOffers();
    }


}
