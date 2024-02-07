package com.propertymanagement.portal.dto.request;

import com.propertymanagement.portal.domain.Customer;
import com.propertymanagement.portal.domain.OfferStatus;
import com.propertymanagement.portal.domain.OfferType;
import com.propertymanagement.portal.domain.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MakeOfferRequest {
    private double offerAmount;
    private OfferType offerType;
}

