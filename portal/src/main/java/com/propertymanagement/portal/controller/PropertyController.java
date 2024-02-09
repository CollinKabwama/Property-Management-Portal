package com.propertymanagement.portal.controller;

import com.propertymanagement.portal.domain.ListingType;
import com.propertymanagement.portal.domain.Offer;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.domain.PropertyType;
import com.propertymanagement.portal.dto.OfferDTO;
import com.propertymanagement.portal.dto.PropertyDTO;
import com.propertymanagement.portal.dto.request.MakeOfferRequest;
import com.propertymanagement.portal.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/properties")
public class PropertyController {

    @Autowired
    PropertyService propertyService;
    public void saveProperty(Property property){
        propertyService.saveProperty(property);
    }

    @GetMapping("/all")
    public ResponseEntity <Set<PropertyDTO>> getAllProperties(){
        Set<PropertyDTO> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);

    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/{id}")
    public ResponseEntity <PropertyDTO>  getPropertyById(@PathVariable("id") Long id) {
        PropertyDTO propertyDTO = propertyService.getPropertyById(id);
        return ResponseEntity.ok(propertyDTO);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/owner/all")
    public ResponseEntity <Set<PropertyDTO>> getAllPropertiesByOwner(){
        Set<PropertyDTO> properties = propertyService.getAllPropertiesByOwner();
        return ResponseEntity.ok(properties);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("/create")
    public ResponseEntity <PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO){
        PropertyDTO createdProperty = propertyService.createProperty(propertyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty);

    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PutMapping("/update/{id}")
    public ResponseEntity <PropertyDTO> updateProperty(@PathVariable("id") Long id, @RequestBody PropertyDTO propertyDTO){
        PropertyDTO updatedProperty = propertyService.updateProperty(id, propertyDTO);
        return ResponseEntity.ok(updatedProperty);
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @DeleteMapping("/delete/{id}")

    public ResponseEntity <String> deleteProperty(@PathVariable("id") Long id){
        boolean isDeleted = propertyService.removeProperty(id);
        if(isDeleted){
            return ResponseEntity.ok("Property with id " + id + " has been deleted successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property with id " + id + " not found");
        }
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("/addProperty")
    public ResponseEntity <PropertyDTO> addProperty(@RequestBody PropertyDTO propertyDTO){
        PropertyDTO addedProperty = propertyService.addProperty(propertyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProperty);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/{id}/offers")
    public ResponseEntity <Set<OfferDTO>> getOffersByPropertyId(@PathVariable("id") Long id){
        Set<OfferDTO> offers = propertyService.getOffersByPropertyId(id);
        return ResponseEntity.ok(offers);

    }


    @GetMapping
    public Page<Property> getProperties(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) ListingType listingType,
            @RequestParam(required = false) Integer minBedRooms,
            @RequestParam(required = false) Integer maxBedRooms,
            @RequestParam(required = false) Double minBathRooms,
            @RequestParam(required = false) Double maxBathRooms,
            @RequestParam(required = false) PropertyType propertyType,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return propertyService.findPropertiesByCriteria(minPrice, maxPrice, listingType, minBedRooms, maxBedRooms,
                minBathRooms, maxBathRooms, propertyType, city, state, pageable);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{propertyId}/offers")
    public Offer makeOffer(@PathVariable Long propertyId, @RequestBody MakeOfferRequest makeOfferRequest){
        return propertyService.makeOffer(propertyId, makeOfferRequest);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{propertyId}/like")
    public void addToFavourites(@PathVariable Long propertyId){
        propertyService.addToFavourites(propertyId);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{propertyId}/unlike")
    public void removeFromFavourites(@PathVariable Long propertyId){
        propertyService.removeFromFavourites(propertyId);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{propertyId}/offers")
    public Offer updateOffer(@PathVariable Long propertyId, @RequestBody MakeOfferRequest makeOfferRequest){
        return propertyService.updateOffer(propertyId, makeOfferRequest);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{propertyId}/offers")
    public void deleteOffer(@PathVariable Long propertyId){
        propertyService.deleteOffer(propertyId);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{propertyId}/offers/{offerId}/accept")
    public void acceptOffer(@PathVariable Long propertyId, @PathVariable Long offerId){
        propertyService.acceptOffer(propertyId, offerId);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{propertyId}/offers/{offerId}/reject")
    public void rejectOffer(@PathVariable Long propertyId, @PathVariable Long offerId){
        propertyService.rejectOffer(propertyId, offerId);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{propertyId}/offers/{offerId}/make-contingent")
    public void makeContingent(@PathVariable Long propertyId, @PathVariable Long offerId){
        propertyService.makeContingent(propertyId, offerId);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{propertyId}/offers/{offerId}/cancel-contingent")
    public void cancelContingent(@PathVariable Long propertyId, @PathVariable Long offerId){
        propertyService.cancelContingent(propertyId, offerId);
    }
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/favourite-properties")
    public Set<Property> getFavouritePropertiesByCustomer(){
        return propertyService.getFavouritePropertiesByCustomer();
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/offers")
    public Set<OfferDTO> getOffersByCustomer(){
        return propertyService.getOffersByCustomer();
    }


    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{propertyId}/offers-illegibility")
    public Boolean makeOffer(@PathVariable Long propertyId){
        return propertyService.illegibilityToMakeOffer(propertyId);
    }
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{propertyId}/can-delete-offer")
    public Boolean canDeleteOffer(@PathVariable Long propertyId){
        return propertyService.canDeleteOffer(propertyId);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/offers-by-owner")
    public List<OfferDTO> offersByOwner(){
        return propertyService.offersByOwner();
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{propertyId}/offers/{offerId}/can-cancel-contingent")
    public Boolean canCancelContingent(@PathVariable Long propertyId, @PathVariable Long offerId){
        return propertyService.canCancelContingent(propertyId, offerId);
    }
    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/owner-properties")
    public Set<Property> getPropertiesBelongingToOwner(){
        return propertyService.getPropertiesBelongingToOwner();
    }

}
