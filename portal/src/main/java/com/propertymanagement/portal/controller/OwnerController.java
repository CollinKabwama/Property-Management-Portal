package com.propertymanagement.portal.controller;


import com.propertymanagement.portal.domain.Owner;
import com.propertymanagement.portal.dto.OwnerDTO;
import com.propertymanagement.portal.dto.PropertyDTO;
import com.propertymanagement.portal.service.impl.OwnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {

    @Autowired
    private OwnerServiceImpl ownerService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity <OwnerDTO> getOwnerById(@PathVariable Long id){
        OwnerDTO ownerDTO = ownerService.getOwnerById(id);

        return ResponseEntity.ok(ownerDTO);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity <Set<Owner>> getAllOwners(){
        Set<Owner> owners = ownerService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity <OwnerDTO> createOwner(@RequestBody OwnerDTO ownerDTO){
        OwnerDTO createdOwner = ownerService.createOwner(ownerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOwner);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity <OwnerDTO> updateOwner(@PathVariable Long id, @RequestBody OwnerDTO ownerDTO){
        OwnerDTO updatedOwner = ownerService.updateOwner(id, ownerDTO);
        return ResponseEntity.ok(updatedOwner);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity <String> deleteOwner(@PathVariable Long id){

        boolean isDeleted = ownerService.deleteOwner(id);
        if(isDeleted){
            return ResponseEntity.ok("Owner with id " + id + " has been deleted successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner with id " + id + " not found");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-property/{ownerId}")
    public ResponseEntity <PropertyDTO> addProperty(@PathVariable Long ownerId, @RequestBody PropertyDTO propertyDTO){

        PropertyDTO addedProperty = ownerService.addProperty(ownerId, propertyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProperty);

    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/remove-property/{ownerId}/{propertyId}")
    public ResponseEntity <String> removeProperty(@PathVariable Long ownerId, @PathVariable Long propertyId){
        boolean isRemoved = ownerService.removeProperty(ownerId, propertyId);
        if(isRemoved){
            return ResponseEntity.ok("Property with id " + propertyId + " has been removed successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property with id " + propertyId + " not found");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/properties/{propertyId}")
    public ResponseEntity <PropertyDTO> getPropertyById(@PathVariable Long propertyId){
        PropertyDTO propertyDTO = ownerService.getPropertyById(propertyId);
        return ResponseEntity.ok(propertyDTO);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update-property/{propertyId}")
    public ResponseEntity <PropertyDTO> updateProperty(@PathVariable Long propertyId, @RequestBody PropertyDTO propertyDTO){
        PropertyDTO updatedProperty = ownerService.updateProperty(propertyId, propertyDTO);
        return ResponseEntity.ok(updatedProperty);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create-property/{ownerId}")
    public ResponseEntity <PropertyDTO> createProperty(@PathVariable Long ownerId, @RequestBody PropertyDTO propertyDTO){
        PropertyDTO createdProperty = ownerService.createProperty(ownerId, propertyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty);
    }

    @PatchMapping("/{ownerId}/enable")
    public ResponseEntity <Void> enableOwner(@PathVariable Long ownerId,@RequestParam boolean isEnabled){
        ownerService.activateOwner(ownerId, isEnabled);
        return ResponseEntity.ok().build();
    }

}
