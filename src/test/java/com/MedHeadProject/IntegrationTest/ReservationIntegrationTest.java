package com.MedHeadProject.IntegrationTest;
import controllers.ReservationController;
import entities.Hospital;
import entities.Speciality;
import requests.ReservationRequest;
import services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import repositories.SpecialityRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private SpecialityRepository specialityRepository;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void makeReservation_WithValidData_ReturnsSuccessResponse() {
        // Arrange
        String patientName = "John Doe";
        String phoneNumber = "1234567890";
        int numPatients = 2;
        int numBeds = 3;
        Long hospitalId = 1L;
        String specialityName = "Cardiology";

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setPatientName(patientName);
        reservationRequest.setPhoneNumber(phoneNumber);
        reservationRequest.setNumPatients(numPatients);
        reservationRequest.setNumBeds(numBeds);
        reservationRequest.setHospital(new Hospital(hospitalId));
        reservationRequest.setSpecialityName(specialityName);

        Speciality speciality = new Speciality();
        speciality.setId(1L);
        speciality.setName(specialityName);

        // Mock the behavior of specialityRepository.findByName()
        when(specialityRepository.findByName(specialityName)).thenReturn(Optional.of(speciality));

        // Mock the behavior of reservationService.createReservation()
        doNothing().when(reservationService).createReservation(reservationRequest);

        // Act
        ResponseEntity<String> response = reservationController.makeReservation(reservationRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("La réservation a été effectuée avec succès.", response.getBody());
        verify(specialityRepository, times(1)).findByName(specialityName);
        verify(reservationService, times(1)).createReservation(reservationRequest);
    }
}
