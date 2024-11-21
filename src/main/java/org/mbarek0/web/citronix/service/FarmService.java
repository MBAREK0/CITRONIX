package org.mbarek0.web.citronix.service;

import org.springframework.data.domain.Page;
import org.mbarek0.web.citronix.domain.entities.Farm;
import org.mbarek0.web.citronix.DTO.SearchFarmDTO;

import java.util.List;
import java.util.UUID;

public interface FarmService {
    Farm save(Farm farm);
    List<Farm> getAllFarms();
    Farm getFarmById(UUID id);

    Farm getFarmByName(String name);

    Farm updateFarm(UUID id, Farm farm);
    void deleteFarm(UUID id);
    Page<Farm> getFarmsWithPagination(int page, int size);
    List<Farm> search(SearchFarmDTO searchFarmDTO);
}
