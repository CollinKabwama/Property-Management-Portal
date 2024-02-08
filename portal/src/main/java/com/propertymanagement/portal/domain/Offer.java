package com.propertymanagement.portal.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.propertymanagement.portal.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    @JsonBackReference // Prevent serialization of the parent
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference // Prevent serialization of the parent
    private Customer customer;

    private LocalDateTime offerDate;

    /*@Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", offerAmount=" + offerAmount +
                ", offerStatus=" + offerStatus +
                ", offerType=" + offerType +
                ", offerDate=" + offerDate +
                '}';
    }*/
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
