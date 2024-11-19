package com.mbarek0.web.citronix.repository;

import com.mbarek0.web.citronix.domain.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, UUID> {
    Optional<Farm> findByIdAndDeletedAtIsNull(UUID id);
    @Query("SELECT f FROM Farm f WHERE f.deletedAt IS NULL")
    Page<Farm> findAllAndDeletedAtIsNull(Pageable pageable);
    Page<Farm> findByNameContainingAndLocationContaining(String name, String location, Pageable pageable);
    Optional<Farm> findByFieldsIdAndDeletedAtIsNull(UUID fieldId);

    @Query("SELECT f FROM Farm f WHERE f.deletedAt IS NULL AND (SELECT SUM(field.area) FROM f.fields field) < 4000")
    Page<Farm> findAllThatTotalFieldsAreaSmallerThan4000(Pageable pageable);



}