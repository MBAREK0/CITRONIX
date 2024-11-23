package org.mbarek0.web.citronix.service.Implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.mbarek0.web.citronix.event.FarmDeletedEvent;
import org.mbarek0.web.citronix.event.FieldDeleteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.mbarek0.web.citronix.domain.entities.Farm;
import org.mbarek0.web.citronix.domain.entities.Field;
import org.mbarek0.web.citronix.repository.FieldRepository;
import org.mbarek0.web.citronix.service.FarmService;
import org.mbarek0.web.citronix.service.FieldService;
import org.mbarek0.web.citronix.exception.Farm.FarmSizeException;
import org.mbarek0.web.citronix.exception.Field.FieldNotFoundException;
import org.mbarek0.web.citronix.exception.InvalidCredentialsException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmService farmService;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    @Override
    public Field addField(Field field) {
        Farm farm = farmService.getFarmById(field.getFarm().getId());

        validateField(farm,field.getArea());

        farm.getFields().add(field);
        field.setFarm(farm);
        return fieldRepository.save(field);
    }

    private void validateField(Farm farm, Double newFieldArea) {
        if (newFieldArea < 0.1) {
            throw new InvalidCredentialsException("Field area must be at least 0.1 hectares (1,000 m²)");
        }

        if (newFieldArea > (0.5 * farm.getArea())) {
            throw new InvalidCredentialsException("Field area cannot exceed 50% of the farm's total area");
        }

        if (farm.getFields().size() >= 10) {
            throw new FarmSizeException("A farm cannot have more than 10 fields");
        }

        double totalArea = farm.getFields().stream()
                .mapToDouble(Field::getArea)
                .sum() + newFieldArea;
        if (totalArea >= farm.getArea()) {
            throw new FarmSizeException("Total field area must be less than the farm's total area");
        }
    }

    @Override
    public Field getFieldById(UUID id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new FieldNotFoundException("Field not found"));
    }


    @Override
    public void deleteField(UUID id) {
        if (id == null) {
            throw new InvalidCredentialsException("Field ID is required");
        }

        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field not found with ID: " + id));

        eventPublisher.publishEvent(new FieldDeleteEvent(field.getId()));

        fieldRepository.delete(field);
    }

    @Override
    public Page<Field> findAllByFarmId(UUID farmId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return fieldRepository.findAllByFarmId(farmId, pageable);
    }

    @Override
    public Page<Field> getFieldsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return fieldRepository.findAll(pageable);
    }

    @Override
    public Field updateField(UUID fieldId, double newArea) {
        if (fieldId == null){
            throw new InvalidCredentialsException("field Id is required");
        }
        Field fieldToUpdate = getFieldById(fieldId);
        validateField(fieldToUpdate.getFarm(),newArea);

        fieldToUpdate.setArea(newArea);
        return fieldRepository.save(fieldToUpdate);
    }



    @Transactional
    @Override
    public void deleteAllFieldsByFarmId(UUID farmId) {
        List<Field> fields = fieldRepository.getFieldsByFarmId(farmId);

        fields.forEach(field -> deleteField(field.getId()));
    }
}