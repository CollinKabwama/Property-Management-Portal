package com.propertymanagement.portal.repository;

import com.propertymanagement.portal.domain.Customer;
import com.propertymanagement.portal.domain.Owner;
import com.propertymanagement.portal.dto.PropertyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.propertymanagement.portal.domain.Property;

import java.util.Set;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findOwnerByUserEmail(String email);



    //get property by owner id

    @Query("SELECT o.properties FROM Owner o WHERE o.id = :ownerId")
    Set<Property> getPropertiesByOwnerId(Long ownerId);

    //update property by owner id
    @Query("UPDATE Owner o SET o.properties = :propertyDTO WHERE o.id = :ownerId")
    PropertyDTO updatePropertyByOwnerId(Long ownerId, PropertyDTO propertyDTO);

    // delete property by owner id



}
