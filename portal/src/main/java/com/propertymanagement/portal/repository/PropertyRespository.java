package com.propertymanagement.portal.repository;

import com.propertymanagement.portal.domain.Property;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRespository extends JpaRepository<Property, Long> {


}
