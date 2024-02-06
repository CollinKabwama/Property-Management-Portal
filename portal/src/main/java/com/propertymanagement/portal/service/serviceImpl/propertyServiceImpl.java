package com.propertymanagement.portal.service.serviceImpl;

import com.propertymanagement.portal.domain.ListingType;
import com.propertymanagement.portal.domain.Property;
import com.propertymanagement.portal.domain.PropertyType;
import com.propertymanagement.portal.repository.PropertyRespository;
import com.propertymanagement.portal.service.PropertyService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class propertyServiceImpl implements PropertyService {
    @Autowired
    PropertyRespository propertyRespository;

    @PersistenceContext
    private EntityManager entityManager;

    public Page<Property> findPropertiesByCriteria(
            Double minPrice, Double maxPrice,
            ListingType listingType, Integer minBedRooms,
            Integer maxBedRooms, Double minBathRooms, Double maxBathRooms, PropertyType propertyType,
            String city, String state, Pageable pageable) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> query = builder.createQuery(Property.class);
        Root<Property> root = query.from(Property.class);
        List<Predicate> predicates = buildPredicates(builder, root, minPrice, maxPrice, listingType, minBedRooms,
                maxBedRooms, minBathRooms, maxBathRooms, propertyType, city, state);

        query.where(predicates.toArray(new Predicate[0]));
        query.select(root);

        List<Property> properties = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Property> countRoot = countQuery.from(Property.class);
        List<Predicate> countPredicates = buildPredicates(builder, countRoot, minPrice, maxPrice, listingType, minBedRooms,
                maxBedRooms, minBathRooms, maxBathRooms, propertyType, city, state);

        countQuery.where(countPredicates.toArray(new Predicate[0]));
        countQuery.select(builder.count(countRoot));

        Long totalItems = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(properties, pageable, totalItems);
    }

    private List<Predicate> buildPredicates(CriteriaBuilder builder, Root<Property> root, Double minPrice,
                                            Double maxPrice, ListingType listingType, Integer minBedRooms,
                                            Integer maxBedRooms, Double minBathRooms, Double maxBathRooms,
                                            PropertyType propertyType, String city, String state) {

        List<Predicate> predicates = new ArrayList<>();

        if (minPrice != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if (listingType != null) {
            predicates.add(builder.equal(root.get("listingType"), listingType));
        }
        if (minBedRooms != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("bedrooms"), minBedRooms));
        }
        if (maxBedRooms != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("bedrooms"), maxBedRooms));
        }
        if (minBathRooms != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("bathrooms"), minBathRooms));
        }
        if (maxBathRooms != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("bathrooms"), maxBathRooms));
        }
        if (propertyType != null) {
            predicates.add(builder.equal(root.get("propertyType"), propertyType));
        }
        if (city != null && !city.isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get("address").get("city")), "%" + city.toLowerCase() + "%"));
        }
        if (state != null && !state.isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get("address").get("state")), "%" + state.toLowerCase() + "%"));
        }
        return predicates;
    }

    @Override
    public Property getPropertyById(long id) {
        return propertyRespository.findById(id).get();
    }
    @Override
    public void saveProperty(Property property) {
        propertyRespository.save(property);
    }

    public List<Property> findAllProperties() {
        return propertyRespository.findAll();
    }

}
