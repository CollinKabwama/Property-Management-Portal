package com.propertymanagement.portal.dto;


import com.propertymanagement.portal.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {

    private Long id;
    private String name;
    private String description;
    private double price;
    private PropertyStatus status;
    private ListingType listingType;
    private PropertyType propertyType;
    private int bathRooms;
    private int bedRooms;
  //  private Owner owner;
   // private Set<Offer> offers = new HashSet<>();
    private AddressDTO address;
    private String imageUrl;
    private LocalDate constructionDate;
   // public void addOffer(Offer offer){
     //   offers.add(offer);
   // }

}
