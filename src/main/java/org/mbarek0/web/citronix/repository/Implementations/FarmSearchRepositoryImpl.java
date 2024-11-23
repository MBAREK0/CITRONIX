package org.mbarek0.web.citronix.repository.Implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.mbarek0.web.citronix.domain.entities.Farm;
import org.mbarek0.web.citronix.repository.FarmSearchRepository;
import org.mbarek0.web.citronix.DTO.SearchFarmDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FarmSearchRepositoryImpl implements FarmSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Farm> findByCriteria(SearchFarmDTO searchFarmDTO) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> query = cb.createQuery(Farm.class);
        Root<Farm> farm = query.from(Farm.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchFarmDTO.getName() != null && !searchFarmDTO.getName().isEmpty()) {
            predicates.add(cb.like(farm.get("name"), "%" + searchFarmDTO.getName() + "%"));
        }
        if (searchFarmDTO.getLocation() != null && !searchFarmDTO.getLocation().isEmpty()) {
            predicates.add(cb.like(farm.get("location"), "%" + searchFarmDTO.getLocation() + "%"));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}
