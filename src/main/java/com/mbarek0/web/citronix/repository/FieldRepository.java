package com.mbarek0.web.citronix.repository;

import com.mbarek0.web.citronix.domain.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FieldRepository extends JpaRepository<Field, UUID> {
    Optional<Field> findByIdAndDeletedAtIsNull(UUID id);
    @Query("SELECT f FROM Field f WHERE f.deletedAt IS NULL")
    Page<Field> findAllAndDeletedAtIsNull(Pageable pageable);
}
