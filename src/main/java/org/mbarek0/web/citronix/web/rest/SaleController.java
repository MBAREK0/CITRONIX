package org.mbarek0.web.citronix.web.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.mbarek0.web.citronix.domain.entities.Sale;
import org.mbarek0.web.citronix.service.SaleService;
import org.mbarek0.web.citronix.web.VM.Sale.SaleCreationVm;
import org.mbarek0.web.citronix.web.VM.Sale.SaleResponseVM;
import org.mbarek0.web.citronix.web.VM.mapper.SaleVMMapper;

import java.util.UUID;

@RestController
@RequestMapping("v1/api/sales")
@AllArgsConstructor
public class SaleController {

    private final SaleService saleService;
    private final SaleVMMapper saleVMMapper;

    @PostMapping("/save/{harvestId}")
    public ResponseEntity<SaleResponseVM> saveTree(@PathVariable UUID harvestId, @RequestBody @Valid SaleCreationVm saleCreationVm) {
        Sale saleToSave = saleVMMapper.toSale(saleCreationVm);
        Sale savedSale = saleService.createSale(harvestId,saleToSave);
        SaleResponseVM saleResponseVM = saleVMMapper.toSaleResponseVM(savedSale);
        return ResponseEntity.status(HttpStatus.CREATED).body(saleResponseVM);
    }

    @DeleteMapping("/delete/{saleId}")
    public ResponseEntity<String> deleteSale(@PathVariable UUID saleId) {
        saleService.deleteSale(saleId);
        return ResponseEntity.ok("Sale deleted successfully");
    }

    @GetMapping("/details/{saleId}")
    public ResponseEntity<SaleResponseVM> getSaleById(@PathVariable UUID saleId) {
        Sale sale = saleService.findById(saleId);
        SaleResponseVM saleResponseVM = saleVMMapper.toSaleResponseVM(sale);
        return ResponseEntity.ok(saleResponseVM);
    }


}
