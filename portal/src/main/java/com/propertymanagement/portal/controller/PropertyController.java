package com.propertymanagement.portal.controller;

import com.propertymanagement.portal.domain.ListingType;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.domain.PropertyType;
import com.propertymanagement.portal.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/properties")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("/{id}")
    public Property getPropertyById(@PathVariable("id") long id) {
        return propertyService.getPropertyById(id);
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







}
