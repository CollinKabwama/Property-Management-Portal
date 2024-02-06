package com.propertymanagement.portal.SampleData;

import com.propertymanagement.portal.domain.*;

public class PropertyDummyData {

    public static Property createSampleProperty1() {
        Property property = new Property();
        property.setId(1L);
        property.setTitle("Cozy Apartment");
        property.setDescription("A cozy apartment with a nice view");
        property.setPrice(150000.0);
        property.setStatus(PropertyStatus.AVAILABLE);
        property.setType(ListingType.SALE);
        property.setNumberOfRooms(2);

        Owner owner1 = new Owner();
        owner1.setId(1L);
        //owner1.setName("John Doe");
        property.setOwner(owner1);

        Address address1 = new Address();
        address1.setId(1);
        address1.setLine1("123 Main St");
        address1.setLine2("Apt 1");
        address1.setCity("SampleCity");
        address1.setPostalCode("12345");
        address1.setState("SampleState");
        property.setAddress(address1);

        property.setImageUrl("https://example.com/cozy-apartment.jpg");
        property.setNumberOfbathrooms(1.5);

        return property;
    }

    public static Property createSampleProperty2() {
        Property property = new Property();
        property.setId(2L);
        property.setTitle("Spacious House");
        property.setDescription("A spacious house with a beautiful garden");
        property.setPrice(250000.0);
        property.setStatus(PropertyStatus.AVAILABLE);
        property.setType(ListingType.SALE);
        property.setNumberOfRooms(4);

        Owner owner2 = new Owner();
        owner2.setId(2L);
       // owner2.setName("Jane Smith");
        property.setOwner(owner2);

        Address address2 = new Address();
        address2.setId(2);
        address2.setLine1("456 Oak St");
        address2.setLine2("House 2");
        address2.setCity("SampleCity");
        address2.setPostalCode("67890");
        address2.setState("SampleState");
        property.setAddress(address2);

        property.setImageUrl("https://example.com/spacious-house.jpg");
        property.setNumberOfbathrooms(3.0);

        return property;
    }

    public static Property createSampleProperty3() {
        Property property = new Property();
        property.setId(3L);
        property.setTitle("Modern Condo");
        property.setDescription("A modern condo with great amenities");
        property.setPrice(180000.0);
        property.setStatus(PropertyStatus.AVAILABLE);
        property.setType(ListingType.RENT);
        property.setNumberOfRooms(1);

        Owner owner3 = new Owner();
        owner3.setId(3L);
      //  owner3.setName("Alice Johnson");
        property.setOwner(owner3);

        Address address3 = new Address();
        address3.setId(3);
        address3.setLine1("789 Elm St");
        address3.setCity("AnotherCity");
        address3.setPostalCode("54321");
        address3.setState("AnotherState");
        property.setAddress(address3);

        property.setImageUrl("https://example.com/modern-condo.jpg");
        property.setNumberOfbathrooms(1.0);

        return property;
    }

    // Add more methods for additional sample properties
}

