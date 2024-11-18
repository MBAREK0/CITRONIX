package com.mbarek0.web.citronix.repository.impl;

import com.mbarek0.web.citronix.domain.Farm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class FarmRepositoryCustomImpl  {

    private final EntityManager entityManager;


    public Page<Farm> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> criteriaQuery = criteriaBuilder.createQuery(Farm.class);
        Root<Farm> farmRoot = criteriaQuery.from(Farm.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(criteriaBuilder.like(farmRoot.get("name"), "%" + name + "%"));
        }

        if (location != null && !location.isEmpty()) {
            predicates.add(criteriaBuilder.like(farmRoot.get("location"), "%" + location + "%"));
        }

        if (minArea != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(farmRoot.get("area"), minArea));
        }

        if (maxArea != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(farmRoot.get("area"), maxArea));
        }

        if (startDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(farmRoot.get("creationDate"), startDate));
        }

        if (endDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(farmRoot.get("creationDate"), endDate));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        var query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Farm> countRoot = countQuery.from(Farm.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, count);
    }
}
