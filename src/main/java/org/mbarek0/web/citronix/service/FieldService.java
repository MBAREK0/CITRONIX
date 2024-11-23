package org.mbarek0.web.citronix.service;

import org.springframework.data.domain.Page;
import org.mbarek0.web.citronix.domain.entities.Field;

import java.util.UUID;

public interface FieldService {

    Field addField(Field field);
    Field getFieldById(UUID id);
    void deleteField(UUID id);
    Page<Field> findAllByFarmId(UUID farmId, int page, int size);
    Page<Field> getFieldsWithPagination(int page, int size);
    Field updateField(UUID fieldId, double area);
    void deleteAllFieldsByFarmId(UUID farmId);

}
