package com.mbarek0.web.citronix.web.rest.controller;

import com.mbarek0.web.citronix.domain.Farm;
import com.mbarek0.web.citronix.domain.Field;
import com.mbarek0.web.citronix.service.FarmService;
import com.mbarek0.web.citronix.service.FieldService;
import com.mbarek0.web.citronix.web.vm.mapper.FarmMapper;
import com.mbarek0.web.citronix.web.vm.mapper.FieldMapper;
import com.mbarek0.web.citronix.web.vm.request.FarmRequestVM;
import com.mbarek0.web.citronix.web.vm.request.FieldRequestVM;
import com.mbarek0.web.citronix.web.vm.response.FarmResponseVM;
import com.mbarek0.web.citronix.web.vm.response.FieldResponseVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/field")
public class FieldController {

    private final FieldMapper fieldMapper;
    private final FieldService fieldService;


    @GetMapping
    public ResponseEntity<Page<FieldResponseVM>> getAllFields(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Field> fields = fieldService.getAllFields(page, size);
        return new ResponseEntity<>(fields.map(fieldMapper::toFieldResponseVM), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldResponseVM> getFieldById(@PathVariable UUID id) {
        Field field = fieldService.getFieldById(id);
        return new ResponseEntity<>(fieldMapper.toFieldResponseVM(field), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<FieldResponseVM> createFarm(@RequestBody @Valid FieldRequestVM fieldVM) {

        Field field = fieldMapper.toField(fieldVM);
        Field createdField = fieldService.createField(field);
        FieldResponseVM fieldResponseVM = fieldMapper.toFieldResponseVM(createdField);
        return new ResponseEntity<>(fieldResponseVM, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FieldResponseVM> softDeleteFarm(@PathVariable UUID id) {
        Field deletedField = fieldService.softDeleteField(id);
        return new ResponseEntity<>(fieldMapper.toFieldResponseVM(deletedField), HttpStatus.OK);
    }


}
