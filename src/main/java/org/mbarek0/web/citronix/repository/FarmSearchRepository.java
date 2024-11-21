package org.mbarek0.web.citronix.repository;

import org.mbarek0.web.citronix.domain.entities.Farm;
import org.mbarek0.web.citronix.DTO.SearchFarmDTO;

import java.util.List;

public interface FarmSearchRepository {
    List<Farm> findByCriteria(SearchFarmDTO searchFarmDTO);
}
