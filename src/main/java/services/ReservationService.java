package services;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import entities.Hospital;
import entities.Speciality;
import entities.Reservation;
import repositories.HospitalRepository;
import repositories.ReservationRepository;
import repositories.SpecialityRepository;
import requests.ReservationRequest;

@Service

public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private HospitalRepository hospitalRepository;
    
    
    public void createReservation(ReservationRequest reservationRequest) {
            // Autres vérifications et validations...

    	 Speciality speciality = reservationRequest.getSpeciality();
         Hospital hospital = hospitalRepository.findById(reservationRequest.getHospitalId())
                 .orElseThrow(() -> new IllegalArgumentException("Hôpital non trouvé avec ID: " + reservationRequest.getHospitalId()));

         Reservation reservation = new Reservation();
         reservation.setPatientName(reservationRequest.getPatientName());
         reservation.setPhoneNumber(reservationRequest.getPhoneNumber());
         reservation.setNumPatients(reservationRequest.getNumPatients());
         reservation.setNumBeds(reservationRequest.getNumBeds());
         reservation.setHospital(hospital);
         reservation.setSpeciality(speciality);

         // Définir la date et l'heure actuelles comme reservationDate
         reservation.setReservationDate(LocalDateTime.now());

         reservationRepository.save(reservation);
      // Mettre à jour le nombre de lits disponibles dans l'hôpital
         int numBeds = reservationRequest.getNumBeds();
         int availableBeds = hospital.getAvailableBeds();
         hospital.setAvailableBeds(availableBeds - numBeds);
         hospitalRepository.save(hospital);
     }

 }

      
        
