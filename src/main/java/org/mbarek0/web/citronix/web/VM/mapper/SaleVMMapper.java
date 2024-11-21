package org.mbarek0.web.citronix.web.VM.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mbarek0.web.citronix.domain.entities.Sale;
import org.mbarek0.web.citronix.web.VM.Sale.SaleCreationVm;
import org.mbarek0.web.citronix.web.VM.Sale.SaleResponseVM;

@Mapper(componentModel = "spring")
public interface SaleVMMapper {

    Sale toSale(SaleCreationVm saleCreationVm);

    @Mapping(source = "sale", target = "revenue", qualifiedByName = "calculateRevenue")
    @Mapping(source = "harvest.totalQuantity", target = "soldQuantity")
    SaleResponseVM toSaleResponseVM(Sale sale);

    @Named("calculateRevenue")
    default double calculateRevenue(Sale sale) {
        return sale.getUnitPrice() * sale.getHarvest().getTotalQuantity();
    }
}
