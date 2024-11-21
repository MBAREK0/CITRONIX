package org.mbarek0.web.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.mbarek0.web.citronix.domain.entities.Harvest;
import org.mbarek0.web.citronix.domain.entities.Sale;

import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
    boolean existsByHarvest(Harvest harvest);
}
