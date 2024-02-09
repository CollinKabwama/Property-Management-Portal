package com.propertymanagement.portal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.propertymanagement.portal.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private Set<Offer> offers = new HashSet<>();

    @OneToMany
    private Set<Property> savedList = new HashSet<>();

    public void addOffer(Offer offer){
        offers.add(offer);
    }
    public void addFavoutite(Property property){
        savedList.add(property);
    }
    public void removeFavourite(Property property){
        savedList.remove(property);
    }
    public void removeOffer(Offer offer){
        offers.remove(offer);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                '}';
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
