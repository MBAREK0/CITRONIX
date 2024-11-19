package com.mbarek0.web.citronix.web.vm.mapper;

import com.mbarek0.web.citronix.domain.Farm;
import com.mbarek0.web.citronix.domain.Field;
import com.mbarek0.web.citronix.web.vm.request.FarmRequestVM;
import com.mbarek0.web.citronix.web.vm.request.FieldRequestVM;
import com.mbarek0.web.citronix.web.vm.response.FarmResponseVM;
import com.mbarek0.web.citronix.web.vm.response.FieldResponseVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {FarmMapper.class})
public interface FieldMapper {
    Field toField(FieldRequestVM farmRequest);
    FieldResponseVM toFieldResponseVM(Field field);

}
