package org.mbarek0.web.citronix.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mbarek0.web.citronix.domain.entities.Farm;
import org.mbarek0.web.citronix.domain.entities.Field;
import org.mbarek0.web.citronix.event.FieldDeleteEvent;
import org.mbarek0.web.citronix.exception.Farm.FarmSizeException;
import org.mbarek0.web.citronix.exception.Field.FieldNotFoundException;
import org.mbarek0.web.citronix.exception.InvalidCredentialsException;
import org.mbarek0.web.citronix.repository.FieldRepository;
import org.mbarek0.web.citronix.service.Implementations.FieldServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {

    @Mock
    FieldRepository fieldRepository;

    @Mock
    FarmService farmService;

    @InjectMocks
    FieldServiceImpl fieldService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private Farm farm;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        farm = Farm.builder()
                .id(UUID.randomUUID())
                .name("farm")
                .location("location")
                .area(100.0)
                .fields(new ArrayList<>())
                .build();
    }

    @Test
    void FieldService_addField_succeed(){
        // Given
        Field field =  Field.builder()
                .area(20.0)
                .farm(farm)
                .build();

        Field savedField =  Field.builder()
                .id(UUID.randomUUID())
                .area(20.0)
                .farm(farm)
                .build();
        when(farmService.getFarmById(farm.getId())).thenReturn(farm);
        when(fieldRepository.save(field)).thenReturn(savedField);

        // When
        Field result = fieldService.addField(field);

        // Then
        assertNotNull(result);
        assertEquals(savedField.getId(), result.getId());
        verify(fieldRepository).save(field);
    }

    @Test
    void FieldService_addField_throwsInvalidCredentialsException_whenFieldAreaTooSmall() {

        // Given
        Field field =  Field.builder()
                .area(0.05)
                .farm(farm)
                .build();
        when(farmService.getFarmById(farm.getId())).thenReturn(farm);

        // When & Then
        assertThrows(InvalidCredentialsException.class, () -> {
            fieldService.addField(field);
        });
        verify(fieldRepository,never()).save(field);
    }

    @Test
    void FieldService_addField_throwsInvalidCredentialsException_whenFieldAreaExceedsFarmArea() {
        // Given
        Field field =  Field.builder()
                .area(70)
                .farm(farm)
                .build();
        when(farmService.getFarmById(farm.getId())).thenReturn(farm);

        // When & Then
        assertThrows(InvalidCredentialsException.class, () -> {
            fieldService.addField(field);
        });
        verify(fieldRepository,never()).save(field);
    }

    @Test
    void FieldService_addField_throwsFarmSizeException_whenFarmHasMoreThan10Fields() {
        // Given
        Field field =  Field.builder()
                .area(20)
                .farm(farm)
                .build();

        for (int i = 0; i < 10; i++) {
            farm.getFields().add(new Field());
        }
        when(farmService.getFarmById(farm.getId())).thenReturn(farm);

        // When & Then
        assertThrows(FarmSizeException.class, () -> {
            fieldService.addField(field);
        });
        verify(fieldRepository,never()).save(field);
    }

    @Test
    void FieldService_getFieldById_returnsField(){

        //Given
        UUID fieldId = UUID.randomUUID();
        Field field =  Field.builder()
                .id(fieldId)
                .area(20)
                .farm(farm)
                .build();
        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        //When
        Field result = fieldService.getFieldById(fieldId);

        //Then
        assertNotNull(result);
        assertEquals(fieldId,result.getId());
        verify(fieldRepository).findById(fieldId);
    }
    @Test
    void FieldService_getFieldById_throwsFieldNotFoundException(){
        //Given
        UUID id = UUID.randomUUID();
        when(fieldRepository.findById(id)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(FieldNotFoundException.class,
                ()->fieldService.getFieldById(id));
        verify(fieldRepository).findById(id);
    }

    @Test
    void deleteField_success() {
        // Arrange
        UUID fieldId = UUID.randomUUID();
        Field field = new Field();
        field.setId(fieldId);

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        // Act
        fieldService.deleteField(fieldId);

        // Assert
        verify(fieldRepository).findById(fieldId);
        verify(eventPublisher).publishEvent(any(FieldDeleteEvent.class));
        verify(fieldRepository).delete(field);
    }

    @Test
    void FieldService_findAllByFarmId_succeed(){
        //Given
        Field field1 =  Field.builder()
                .id(UUID.randomUUID())
                .area(20)
                .farm(farm)
                .build();
        Field field2 =  Field.builder()
                .id(UUID.randomUUID())
                .area(30)
                .farm(farm)
                .build();
        List<Field> fieldList = List.of(field1,field2);
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        Page<Field> fieldPage = new PageImpl<>(fieldList);
        when(fieldRepository.findAllByFarmId(farm.getId(),pageable)).thenReturn(fieldPage);

        // When
        Page<Field> result = fieldService.findAllByFarmId(farm.getId(),page,size);

        //Then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(fieldRepository).findAllByFarmId(farm.getId(),pageable);
    }

    @Test
    void FieldService_findAllByFarmId_NoFieldsFound(){

        //Given
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        when(fieldRepository.findAllByFarmId(farm.getId(),pageable)).thenReturn(Page.empty());

        //When
        Page<Field> result = fieldService.findAllByFarmId(farm.getId(),page,size);

        //Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(fieldRepository).findAllByFarmId(farm.getId(),pageable);
    }
}