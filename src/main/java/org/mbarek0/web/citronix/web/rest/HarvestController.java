package org.mbarek0.web.citronix.web.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.mbarek0.web.citronix.domain.entities.Harvest;
import org.mbarek0.web.citronix.domain.enums.Season;
import org.mbarek0.web.citronix.service.HarvestService;
import org.mbarek0.web.citronix.web.VM.Harvest.HarvestCreationVM;
import org.mbarek0.web.citronix.web.VM.Harvest.HarvestResponseVM;
import org.mbarek0.web.citronix.web.VM.mapper.HarvestCreationVMMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/api/harvest")
@AllArgsConstructor
public class HarvestController {

    private final HarvestService harvestService;
    private final HarvestCreationVMMapper harvestCreationVMMapper;

    @PostMapping("/{fieldId}")
    public ResponseEntity<HarvestResponseVM> createHarvest(
            @PathVariable UUID fieldId,
            @Valid @RequestBody HarvestCreationVM harvestCreationVM) {
        Harvest harvest = harvestCreationVMMapper.toHarvest(harvestCreationVM);
        Harvest createdHarvest = harvestService.createHarvest(fieldId,harvest);

        return ResponseEntity.status(HttpStatus.CREATED).body(HarvestResponseVM.fromEntity(createdHarvest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHarvest(@PathVariable UUID id) {
        harvestService.delete(id);
        return ResponseEntity.ok("harvest deleted successfully");
    }

    @GetMapping("/season/{season}")
    public ResponseEntity<List<HarvestResponseVM>> getHarvestsBySeason(@PathVariable Season season) {
        List<Harvest> harvests = harvestService.getHarvestsBySeason(season);
        List<HarvestResponseVM> response = harvests.stream()
                .map(HarvestResponseVM::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

}
