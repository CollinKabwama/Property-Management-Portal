package com.propertymanagement.portal.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDTO {

    private Long id;
    private UserDTO user;
    private Set<PropertyDTO> properties = new HashSet<>();


}
