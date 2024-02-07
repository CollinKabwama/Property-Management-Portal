package com.propertymanagement.portal.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.propertymanagement.portal.domain.Customer;
import com.propertymanagement.portal.domain.OfferStatus;
import com.propertymanagement.portal.domain.OfferType;
import com.propertymanagement.portal.domain.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OfferDTO {

    private Long id;
    private double offerAmount;
    private OfferStatus offerStatus;
    private OfferType offerType;
    private Property property;

    private Customer customer;

    private LocalDateTime offerDate;
}
