package org.mbarek0.web.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.mbarek0.web.citronix.domain.entities.Harvest;
import org.mbarek0.web.citronix.domain.enums.Season;

import java.util.List;
import java.util.UUID;

public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
    List<Harvest> findBySeason(Season season);
}
