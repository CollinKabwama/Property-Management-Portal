package com.propertymanagement.portal.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Integer id;

    private String line1;

    private String line2;

    private String city;

    private String postalCode;

    private String state;

    private String country;
}
