package com.mbarek0.web.citronix.web.vm.mapper;

import com.mbarek0.web.citronix.domain.Farm;
import com.mbarek0.web.citronix.web.vm.request.FarmRequestVM;
import com.mbarek0.web.citronix.web.vm.response.FarmResponseVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmMapper {
    Farm toFarm(FarmRequestVM farmRequest);
    FarmResponseVM toFarmResponseVM(Farm farm);

}
