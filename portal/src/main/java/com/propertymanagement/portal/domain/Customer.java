package com.propertymanagement.portal.domain;

import com.propertymanagement.portal.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "customer")
    private Set<Offer> offers = new HashSet<>();

    @OneToMany
    private Set<Property> savedList = new HashSet<>();

    public void addOffer(Offer offer){
        offers.add(offer);
    }
    public void addFavoutite(Property property){
        savedList.add(property);
    }

}
