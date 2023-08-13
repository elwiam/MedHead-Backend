package com.MedHeadProject.UnitTests;

import controllers.SpecialityController;
import entities.Speciality;
import services.SpecialityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialityControllerUnitTest {
    @Mock
    private SpecialityService specialityService;

    private SpecialityController specialityController;

    @BeforeEach
    public void setUp() {
        specialityService = Mockito.mock(SpecialityService.class);
        specialityController = new SpecialityController(specialityService);
    }

    @Test
    public void testGetAllSpecialities() {
        // Arrange
        Speciality speciality1 = new Speciality();
        speciality1.setId(1L);
        speciality1.setName("Speciality 1");

        Speciality speciality2 = new Speciality();
        speciality2.setId(2L);
        speciality2.setName("Speciality 2");

        List<Speciality> specialities = List.of(speciality1, speciality2);
        Mockito.when(specialityService.getSpecialities()).thenReturn(specialities);

        // Act
        ResponseEntity<List<Speciality>> response = specialityController.getAllSpecialities();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(specialities, response.getBody());
    }
}
