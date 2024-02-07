package com.propertymanagement.portal.repository;

import com.propertymanagement.portal.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;



import org.springframework.stereotype.Repository;

@Repository

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
