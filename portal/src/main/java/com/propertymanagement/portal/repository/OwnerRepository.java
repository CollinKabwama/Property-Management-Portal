package com.propertymanagement.portal.repository;

import com.propertymanagement.portal.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
