package org.mbarek0.web.citronix.service;

import org.mbarek0.web.citronix.domain.entities.Sale;

import java.util.UUID;

public interface SaleService {
    Sale createSale(UUID harvestId, Sale sale);
    void deleteSale(UUID saleId);
    Sale findById(UUID saleId);
}
