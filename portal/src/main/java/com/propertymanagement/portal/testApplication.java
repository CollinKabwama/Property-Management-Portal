package com.propertymanagement.portal;

import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.repository.PropertyRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class testApplication implements CommandLineRunner {

    @Autowired
    private PropertyRespository propertyRepository;

    public static void main(String[] args) {
        SpringApplication.run(testApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Property property1 = new Property("Luxury Villa", "Beautiful villa with ocean view", 500000.0);
        Property property2 = new Property("Cozy Apartment", "Modern apartment in the city center", 150000.0);
        Property property3 = new Property("Spacious House", "Family-friendly house with a large garden", 300000.0);

        propertyRepository.save(property1);
        propertyRepository.save(property2);
        propertyRepository.save(property3);
    }
}
