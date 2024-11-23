package org.mbarek0.web.citronix.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.mbarek0.web.citronix.domain.entities.Tree;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree, UUID> {

    long countByFieldId(UUID fieldId);
    Page<Tree> findAllByFieldId(UUID fieldId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Tree t WHERE t.field.id = :fieldId")
    void deleteAllByFieldId(@Param("fieldId") UUID fieldId);
}
