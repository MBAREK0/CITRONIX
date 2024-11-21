package org.mbarek0.web.citronix.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.mbarek0.web.citronix.domain.entities.Field;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {
    Page<Field> findAllByFarmId(UUID farmId, Pageable pageable);

    @Query("DELETE FROM Field f WHERE f.farm.id = :farmId")
    void deleteByFarmId(@Param("farmId") UUID farmId);


    @Query("SELECT f FROM Field f WHERE f.farm.id = :farmId")
    List<Field> getFieldsByFarmId(@Param("farmId") UUID farmId);

}
