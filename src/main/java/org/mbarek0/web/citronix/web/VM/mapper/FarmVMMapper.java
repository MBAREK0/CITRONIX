package org.mbarek0.web.citronix.web.VM.mapper;

import org.mapstruct.Mapper;
import org.mbarek0.web.citronix.domain.entities.Farm;
import org.mbarek0.web.citronix.web.VM.Farm.FarmVM;

@Mapper(componentModel = "spring")
public interface FarmVMMapper {
    Farm toFarm(FarmVM farmVM);
    FarmVM toFarmVM(Farm farm);
}
