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
    private final FieldService fieldService;

    public Page<Farm> getAllFarms(int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Farm> farms = farmRepository.findAllAndDeletedAtIsNull(pageable);
        return farms;
    }


    public Page<Farm> searchFarms(String name, String location, Pageable pageable) {
        return farmRepository.findByNameContainingAndLocationContaining(name, location, pageable);
    }

    public Optional<Farm> getFarmById(UUID id) {
        return farmRepository.findByIdAndDeletedAtIsNull(id);
    }

    public Farm getFarmByFieldId(UUID fieldId) {
        Optional<Farm> opFarm =  farmRepository.findByFieldsIdAndDeletedAtIsNull(fieldId);
        return opFarm.orElseThrow(() -> new RuntimeException("Farm not found"));
    }

    public Page<Farm> getAllFarmsThatTotalFieldsAreaSmallerThan4000(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return farmRepository.findAllThatTotalFieldsAreaSmallerThan4000(pageable);
    }

    public Farm createFarm(Farm farm) {
        Farm farm1  =  farmRepository.save(farm);

        if (farm1.getFields().size() > 10) {
            throw new RuntimeException("Farm cannot have more than 10 fields");
        }

        if (!farm1.getFields().isEmpty())
            fieldService.createFields(farm1.getFields());

        if (farm1.getFields().stream().map(f -> f.getArea()).reduce(0.0, Double::sum) > farm.getArea()) {
            throw new RuntimeException("Farm cannot have fields with total area greater than farm area");
        }
        return farm1;
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
