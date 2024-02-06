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
        Property property1 = PropertyDummyData.createSampleProperty1();
        Property property2 = PropertyDummyData.createSampleProperty2();
        Property property3 = PropertyDummyData.createSampleProperty3();
        // Add more properties if needed

        propertyRepository.saveAll(List.of(property1, property2, property3));
    }
}

