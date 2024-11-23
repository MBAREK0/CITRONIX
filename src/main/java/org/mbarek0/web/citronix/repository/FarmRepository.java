package org.mbarek0.web.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.mbarek0.web.citronix.domain.entities.Farm;

import java.util.UUID;

public interface FarmRepository extends JpaRepository<Farm, UUID> {
    Farm findByName(String name);
}
