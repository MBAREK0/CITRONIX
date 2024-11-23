package org.mbarek0.web.citronix.service.Implementations;

import lombok.AllArgsConstructor;
import org.mbarek0.web.citronix.event.FarmDeletedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.mbarek0.web.citronix.domain.entities.Farm;
import org.mbarek0.web.citronix.repository.FarmRepository;
import org.mbarek0.web.citronix.repository.FarmSearchRepository;
import org.mbarek0.web.citronix.DTO.SearchFarmDTO;
import org.mbarek0.web.citronix.service.FarmService;
import org.mbarek0.web.citronix.exception.Farm.FarmNotFoundException;
import org.mbarek0.web.citronix.exception.Farm.InvalidFarmException;
import org.mbarek0.web.citronix.exception.InvalidCredentialsException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmSearchRepository farmSearchRepository;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public Farm save(Farm farm) {
        if (farm == null){
            throw new InvalidFarmException("Farm object cannot be null.");
        }
        if (this.getFarmByName(farm.getName()) != null){
            throw new InvalidFarmException("Farm already exists.");
        }
        return farmRepository.save(farm);
    }

    @Override
    public List<Farm> getAllFarms() {
        return farmRepository.findAll();
    }

    @Override
    public Page<Farm> getFarmsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return farmRepository.findAll(pageable);
    }

    @Override
    public Farm getFarmById(UUID id) {
        if (id==null){
            throw new InvalidCredentialsException("farm id is required");
        }
        return farmRepository.findById(id)
                .orElseThrow(()-> new FarmNotFoundException("farm not found"));
    }

    @Override
    public Farm getFarmByName(String name) {
        if (name == null){
            throw new InvalidCredentialsException("farm name is required");
        }
        return farmRepository.findByName(name);
    }


    @Override
    public Farm updateFarm(UUID id, Farm farm) {
        if (id == null){
            throw new InvalidCredentialsException("farm id is required");
        }
        Farm farmToUpdate = this.getFarmById(id);
        if (farm == null){
            throw new InvalidFarmException("invalid farm");
        }
        farmToUpdate.setName(farm.getName());
        farmToUpdate.setArea(farm.getArea());
        farmToUpdate.setLocation(farm.getLocation());
        farmToUpdate.setCreationDate(farm.getCreationDate());
        return farmRepository.save(farmToUpdate);
    }

    @Transactional
    @Override
    public void deleteFarm(UUID id) {
        if (id == null){
            throw new InvalidCredentialsException("farm id is required");
        }

        Farm farmToDelete = this.getFarmById(id);

        eventPublisher.publishEvent(new FarmDeletedEvent(id));

        farmRepository.delete(farmToDelete);


    }

    @Override
    public List<Farm> search(SearchFarmDTO searchFarmDTO) {
        return farmSearchRepository.findByCriteria(searchFarmDTO);
    }
}
