package org.mbarek0.web.citronix.web.VM.mapper;

import org.mapstruct.Mapper;
import org.mbarek0.web.citronix.domain.entities.Harvest;
import org.mbarek0.web.citronix.web.VM.Harvest.HarvestCreationVM;

@Mapper(componentModel = "spring")
public interface HarvestCreationVMMapper {
    Harvest toHarvest(HarvestCreationVM harvestCreationVM);
    HarvestCreationVM toHarvestCreationVM(Harvest harvest);
}
