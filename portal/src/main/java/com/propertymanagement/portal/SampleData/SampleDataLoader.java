package com.propertymanagement.portal.SampleData;

import com.propertymanagement.portal.domain.Address;
import com.propertymanagement.portal.domain.ListingType;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.domain.PropertyType;
import com.propertymanagement.portal.repository.PropertyRespository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
@AllArgsConstructor

@Component
public class SampleDataLoader implements CommandLineRunner {

    @Autowired
     PropertyRespository propertyRepository;


    @Override
    public void run(String... args) throws Exception {
        // Insert sample data into the database
        insertSampleData();
    }

    private void insertSampleData() {
//        Property property1 = new Property(1L, 150000.0, ListingType.SALE, 2, 1.5, PropertyType.APARTMENT, new Address("SampleCity", "SampleState"));
//        Property property2 = new Property(2L, 200000.0, ListingType.RENT, 3, 2.0, PropertyType.HOUSE, new Address("SampleCity", "SampleState"));
//        Property property3 = new Property(3L, 120000.0, ListingType.SALE, 2, 1.0, PropertyType.CONDO, new Address("AnotherCity", "AnotherState"));
//        Property property4 = new Property(4L, 180000.0, ListingType.RENT, 3, 2.5, PropertyType.APARTMENT, new Address("AnotherCity", "AnotherState"));
//
//        propertyRepository.saveAll(List.of(property1, property2, property3, property4));
    }
}

