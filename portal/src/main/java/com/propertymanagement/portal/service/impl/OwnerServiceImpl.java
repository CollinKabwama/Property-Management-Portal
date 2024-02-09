package com.propertymanagement.portal.service.impl;

import com.propertymanagement.portal.domain.Address;
import com.propertymanagement.portal.domain.Owner;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.domain.PropertyStatus;
import com.propertymanagement.portal.dto.AddressDTO;
import com.propertymanagement.portal.dto.OwnerDTO;
import com.propertymanagement.portal.dto.PropertyDTO;
import com.propertymanagement.portal.dto.UserDTO;
import com.propertymanagement.portal.repository.OwnerRepository;
import com.propertymanagement.portal.repository.PropertyRepository;
import com.propertymanagement.portal.service.OwnerService;
import com.propertymanagement.portal.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ModelMapper modelMapper;


    public OwnerDTO getOwnerById(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Owner not found"));

        return convertToDTO(owner);
    }

    public Set<Owner> getAllOwners() {

        return new HashSet<>(ownerRepository.findAll());


       /* return owners.stream()
                .map(owner -> modelMapper.map(owner, OwnerDTO.class))
                .collect(Collectors.toSet());*/
    }
    public OwnerDTO createOwner(OwnerDTO ownerDTO) {
        Owner owner = convertToEntity(ownerDTO);
        owner = ownerRepository.save(owner);
        return convertToDTO(owner);
    }

    public void activateOwner(Long ownerId, boolean isEnabled) {

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NoSuchElementException("Owner not found"));
        owner.setEnabled(isEnabled);
        ownerRepository.save(owner);
    }
    public OwnerDTO updateOwner(Long id, OwnerDTO ownerDTO) {

        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Owner not found"));

        User user = existingOwner.getUser();

        if(ownerDTO.getUser() != null){
            UserDTO userDTO = ownerDTO.getUser();
            user.setFirstname(userDTO.getFirstname());
            user.setLastname(userDTO.getLastname());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());

        }

        if (ownerDTO.getProperties() != null) {
            Set<Property> updatedProperties = ownerDTO.getProperties().stream()
                    .map(propertyDTO -> modelMapper.map(propertyDTO, Property.class))
                    .collect(Collectors.toSet());
            existingOwner.setProperties(updatedProperties);
        }

        Owner updatedOwner = ownerRepository.save(existingOwner);
        return modelMapper.map(updatedOwner, OwnerDTO.class);

    }

    public boolean deleteOwner(Long id) {

        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        if(optionalOwner.isPresent()){
            Owner ownerToDelete = optionalOwner.get();
            ownerRepository.delete(ownerToDelete);
            return true;
        }
        else {
            return false;
        }
    }

    public PropertyDTO addProperty(Long ownerId, PropertyDTO propertyDTO){
        // We discussed that the id is removed for security reasons

        Owner owner = ownerRepository.findById(ownerId)   // We discussed that the id is removed for security reasons
                .orElseThrow(() -> new NoSuchElementException("Owner not found"));

        Property property = modelMapper.map(propertyDTO, Property.class);
        property.setOwner(owner);

        Property savedProperty = propertyRepository.save(property);

        return modelMapper.map(savedProperty, PropertyDTO.class);

    }

    public boolean removeProperty(Long ownerId, Long propertyId){

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NoSuchElementException("Owner not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new NoSuchElementException("Property not found"));

        if(!owner.getProperties().contains(property)){
            throw new IllegalArgumentException("Property not found in owner's properties");
        }

        if(PropertyStatus.PENDING.equals(property.getStatus())){
            throw new IllegalArgumentException("Cannot remove property that is Pending");
        }
        owner.getProperties().remove(property);
        propertyRepository.delete(property);

        return true;
    }

    public PropertyDTO getPropertyById( Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new NoSuchElementException("Property not found or not accessible by owner"));
        return modelMapper.map(property, PropertyDTO.class);
    }

    public PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO) {//
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new NoSuchElementException("Property not found or not accessible by owner"));

        if (PropertyStatus.PENDING.equals(property.getStatus())) {
            throw new IllegalStateException("Cannot update property while it is under Pending status");
        }
        property.setName(propertyDTO.getName());
        property.setDescription(propertyDTO.getDescription());
        property.setPrice(propertyDTO.getPrice());
        property.setBathRooms(propertyDTO.getBathRooms());
        property.setBedRooms(propertyDTO.getBedRooms());

        AddressDTO addressDTO = propertyDTO.getAddress();
        Address address = new Address();
        address.setCity(addressDTO.getLine1());
        address.setLine1(addressDTO.getLine2());
        address.setLine2(addressDTO.getCity());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());

        property.setImageUrl(propertyDTO.getImageUrl());
        property.setConstructionDate(propertyDTO.getConstructionDate());
        property.setStatus(propertyDTO.getStatus());

        Property updatedProperty = propertyRepository.save(property);
        return modelMapper.map(updatedProperty, PropertyDTO.class);
    }

    public PropertyDTO createProperty(Long ownerId, PropertyDTO propertyDTO) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NoSuchElementException("Owner not found"));
        Property property = modelMapper.map(propertyDTO, Property.class);
        property.setOwner(owner);
        Property savedProperty = propertyRepository.save(property);
        return modelMapper.map(savedProperty, PropertyDTO.class);
    }

    public OwnerDTO convertToDTO(Owner owner){

        return modelMapper.map(owner, OwnerDTO.class);
    }

    public Owner convertToEntity(OwnerDTO ownerDTO){

        return modelMapper.map(ownerDTO, Owner.class);
    }

}
