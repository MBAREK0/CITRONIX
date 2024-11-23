package org.mbarek0.web.citronix.service;

import org.mbarek0.web.citronix.domain.entities.Harvest;
import org.mbarek0.web.citronix.domain.enums.Season;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface HarvestService {

    Harvest createHarvest(UUID fieldId, Harvest harvest);
    Harvest findById(UUID id);
    void delete(UUID id);
    List<Harvest> getHarvestsBySeason(Season season);

    @Transactional
    void deleteAllHarvestDetailsByTreeId(UUID treeId);
}
