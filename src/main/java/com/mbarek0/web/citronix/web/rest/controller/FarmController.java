package com.mbarek0.web.citronix.web.rest.controller;

import com.mbarek0.web.citronix.domain.Farm;
import com.mbarek0.web.citronix.service.FarmService;
import com.mbarek0.web.citronix.web.vm.mapper.FarmMapper;
import com.mbarek0.web.citronix.web.vm.request.FarmRequestVM;
import com.mbarek0.web.citronix.web.vm.response.FarmResponseVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/farm")
public class FarmController {

    private final FarmService farmService;
    private final FarmMapper farmMapper;

    /**
     * Get a paginated list of all farms.
     * @param page Page number (default is 0).
     * @param size Page size (default is 10).
     * @return Paginated list of all farms.
     */
    @GetMapping
    public ResponseEntity<Page<FarmResponseVM>> getAllFarms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Farm> farms = farmService.getAllFarms(page, size);
        return new ResponseEntity<>(farms.map(farmMapper::toFarmResponseVM), HttpStatus.OK);
    }

    /**
     * Search farms with multiple criteria and pagination.
     * @param name      Farm name (optional).
     * @param location  Farm location (optional).
     * @param minArea   Minimum area (optional).
     * @param maxArea   Maximum area (optional).
     * @param startDate Start date (optional).
     * @param endDate   End date (optional).
     * @param page      Page number (default is 0).
     * @param size      Page size (default is 10).
     * @return Paginated list of matching farms.
     */
    @GetMapping("/search")
    public ResponseEntity<Page<FarmResponseVM>> searchFarms(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Farm> farms = farmService.searchFarms(name, location, pageable);
        return new ResponseEntity<>(farms.map(farmMapper::toFarmResponseVM), HttpStatus.OK);
    }

    /**
     * Get a specific farm by ID.
     * @param id The ID of the farm.
     * @return The farm if found, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FarmResponseVM> getFarmById(@PathVariable UUID id) {
        Optional<Farm> farm = farmService.getFarmById(id);
        return farm.map(f -> ResponseEntity.ok(farmMapper.toFarmResponseVM(f)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new farm.
     * @param farmVM The farm to create.
     * @return The created farm.
     */
    @PostMapping
    public ResponseEntity<FarmResponseVM> createFarm(@RequestBody @Valid FarmRequestVM farmVM) {

        Farm farm = farmMapper.toFarm(farmVM);
        farm = farmService.createFarm(farm);
        FarmResponseVM farmResponseVM = farmMapper.toFarmResponseVM(farm);
        return new ResponseEntity<>(farmResponseVM, HttpStatus.CREATED);
    }

    /**
     * Soft delete a farm by setting the 'deletedAt' timestamp.
     * @param id The ID of the farm to delete.
     * @return The deleted farm, or 404 if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<FarmResponseVM> softDeleteFarm(@PathVariable UUID id) {
        Optional<Farm> deletedFarm = farmService.softDeleteFarm(id);
        return deletedFarm.map(f -> ResponseEntity.ok(farmMapper.toFarmResponseVM(f)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Restore a soft-deleted farm by removing the 'deletedAt' timestamp.
     * @param id The ID of the farm to restore.
     * @return The restored farm, or 404 if not found.
     */
    @PutMapping("/restore/{id}")
    public ResponseEntity<FarmResponseVM> restoreFarm(@PathVariable UUID id) {
        Optional<Farm> restoredFarm = farmService.restoreFarm(id);
        return restoredFarm.map(f -> ResponseEntity.ok(farmMapper.toFarmResponseVM(f)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
