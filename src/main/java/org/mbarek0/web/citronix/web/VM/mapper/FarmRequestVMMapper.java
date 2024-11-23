package org.mbarek0.web.citronix.web.VM.mapper;

import org.mapstruct.Mapper;
import org.mbarek0.web.citronix.domain.entities.Farm;
import org.mbarek0.web.citronix.web.VM.Farm.FarmRequestVM;

@Mapper(componentModel = "spring")
public interface FarmRequestVMMapper {
    Farm toFarm(FarmRequestVM farmRequestVM);
    FarmRequestVM toFarmCreationVM(Farm farm);
}
