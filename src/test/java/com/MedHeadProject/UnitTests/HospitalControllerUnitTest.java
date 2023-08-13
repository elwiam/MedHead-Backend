package com.MedHeadProject.UnitTests;




import controllers.HospitalController;
import entities.Hospital;
import services.HospitalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HospitalControllerUnitTest {

    @Mock
    private HospitalService hospitalService;

    private HospitalController hospitalController;

    @BeforeEach
    public void setUp() {
        hospitalService = Mockito.mock(HospitalService.class);
        hospitalController = new HospitalController(hospitalService, null);
    }

    @Test
    public void testGetAllHospitals() {
        // Arrange
        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setName("Hospital 1");

        Hospital hospital2 = new Hospital();
        hospital2.setId(2L);
        hospital2.setName("Hospital 2");

        List<Hospital> hospitals = Arrays.asList(hospital1, hospital2);
        Mockito.when(hospitalService.getAllHospitals()).thenReturn(hospitals);

        // Act
        ResponseEntity<Iterable<Hospital>> response = hospitalController.getAllHospitals();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hospitals, response.getBody());
    }
}
