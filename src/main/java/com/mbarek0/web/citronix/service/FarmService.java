package com.mbarek0.web.citronix.service;

import com.mbarek0.web.citronix.domain.Farm;
import com.mbarek0.web.citronix.repository.FarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FarmService {

    private final FarmRepository farmRepository;

    public Page<Farm> getAllFarms(int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Farm> farms = farmRepository.findAllAndDeletedAtIsNull(pageable);
        return farms;
    }


    /**
     * Search farms with multiple criteria and pagination.
     * @param name
     * @param location
     * @param pageable
     * @return
     */
    public Page<Farm> searchFarms(String name, String location, Pageable pageable) {
        return farmRepository.findByNameContainingAndLocationContaining(name, location, pageable);
    }

    public Optional<Farm> getFarmById(UUID id) {
        return farmRepository.findByIdAndDeletedAtIsNull(id);
    }

    public Farm createFarm(Farm farm) {
        return farmRepository.save(farm);
    }

    public Optional<Farm> softDeleteFarm(UUID id) {
        Optional<Farm> farm = getFarmById(id);
        if (farm.isPresent()) {
            Farm existingFarm = farm.get();
            existingFarm.delete();
            farmRepository.save(existingFarm);
            return Optional.of(existingFarm);
        }
        return Optional.empty();
    }

    public Optional<Farm> restoreFarm(UUID id) {
        Optional<Farm> farm = farmRepository.findById(id);
        farm.ifPresent(f -> {
            f.setDeletedAt(null);
            farmRepository.save(f);
        });
        return farm;
    }
}
