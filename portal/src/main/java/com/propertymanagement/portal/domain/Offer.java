package com.propertymanagement.portal.domain;


import com.propertymanagement.portal.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double offerAmount;

    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;

    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
