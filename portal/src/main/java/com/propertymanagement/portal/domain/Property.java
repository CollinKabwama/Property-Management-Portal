package com.propertymanagement.portal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.propertymanagement.portal.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String description;
    private double price;

    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    @Enumerated(EnumType.STRING)
    private ListingType listingType;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;


    private int bathRooms;
    private int bedRooms;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @JsonManagedReference // Include offers in serialization
    @OneToMany(mappedBy = "property", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Offer> offers = new HashSet<>();

    @ManyToOne (cascade = CascadeType.ALL)
    private Address address;

    private String imageUrl;
    private LocalDate constructionDate;

    public void addOffer(Offer offer){
        offers.add(offer);


    }
    public void removeOffer(Offer offer){
        offers.remove(offer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }




}
