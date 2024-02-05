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
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "owner")
    private Set<Property> properties = new HashSet<>();

    public void addProperty(Property property){
        properties.add(property);
    }

}
