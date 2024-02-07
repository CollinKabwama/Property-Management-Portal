package com.propertymanagement.portal.repository;

import com.propertymanagement.portal.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
