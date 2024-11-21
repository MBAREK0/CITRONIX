package org.mbarek0.web.citronix.web.VM.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mbarek0.web.citronix.domain.entities.Field;
import org.mbarek0.web.citronix.web.VM.Field.FieldCreationVM;
import org.mbarek0.web.citronix.web.VM.Field.FieldResponseVM;

@Mapper(componentModel = "spring")
public interface FieldVMMapper {

    @Mapping(source = "farmId", target = "farm.id")
    Field toField(FieldCreationVM fieldCreationVM);

    @Mapping(source = "farm.name", target = "farmName")
    @Mapping(source = "farm.location", target = "farmLocation")
    FieldResponseVM toFieldResponseVM(Field field);
}
