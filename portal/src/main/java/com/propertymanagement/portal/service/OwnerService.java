package com.propertymanagement.portal.service;

import com.propertymanagement.portal.domain.Owner;
import com.propertymanagement.portal.dto.OwnerDTO;
import com.propertymanagement.portal.dto.PropertyDTO;

import java.util.Set;

public interface OwnerService {

    OwnerDTO getOwnerById(Long id);

    Set<Owner> getAllOwners();

    OwnerDTO createOwner(OwnerDTO ownerDTO);

    OwnerDTO updateOwner(Long id, OwnerDTO ownerDTO);

    boolean deleteOwner(Long id);

    void activateOwner(Long OwnerId, boolean isEnabled);

    PropertyDTO addProperty(Long ownerId, PropertyDTO propertyDTO);


    boolean removeProperty(Long ownerIdy, Long propertyId);


 //   Set<PropertyDTO> getPropertiesByOwnerId(Long id);

     PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO);

    public PropertyDTO createProperty(Long ownerId, PropertyDTO propertyDTO);

    public OwnerDTO convertToDTO(Owner owner);

    public Owner convertToEntity(OwnerDTO ownerDTO);



}
