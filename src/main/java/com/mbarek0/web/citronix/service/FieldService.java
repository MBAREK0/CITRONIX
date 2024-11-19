package com.mbarek0.web.citronix.service;

import com.mbarek0.web.citronix.domain.Farm;
import com.mbarek0.web.citronix.domain.Field;
import com.mbarek0.web.citronix.repository.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FieldService {
    private final FieldRepository fieldRepository;

    public Field getFieldById(UUID id) {
        Optional<Field> opField =  fieldRepository.findByIdAndDeletedAtIsNull(id);
        opField.ifPresent(f -> {
            if (f.getFarm().isDeleted()) {
                throw new RuntimeException("Farm is deleted");
            }
        });
        return opField.orElseThrow(() -> new RuntimeException("Field not found"));
    }

    public Page<Field> getAllFields(int page,int size){
        Pageable pageable = PageRequest.of(page, size);
        return fieldRepository.findAllAndDeletedAtIsNull(pageable);
    }

    public Field createField(Field field) {

        if(field.getArea() < 1000){
            throw new RuntimeException("La superficie d'un champ doit être au minimum de 0.1 hectare");
        }

        Farm farm = field.getFarm();

        if(field.getArea() > farm.getArea()/2) {
            throw new RuntimeException("Field area cannot be greater than 50% of the farm area");
        }

        farm.getFields().add(field);
        if (farm.getFields().size() > 10) {
            throw new RuntimeException("Farm cannot have more than 10 fields");
        }
        return fieldRepository.save(field);
    }

    public Set<Field> createFields(Set<Field> fields) {
        fields.forEach(this::createField);
        return fields;
    }

    public Field softDeleteField(UUID id) {
        Field field = getFieldById(id);
        field.delete();
        return fieldRepository.save(field);
    }
}
