package controllers;

import entities.Hospital;
import entities.Reservation;
import entities.Speciality;
import repositories.ReservationRepository;
import repositories.SpecialityRepository;
import requests.ReservationRequest;
import services.HospitalService;
import services.ReservationService;
import services.SpecialityService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repositories.HospitalRepository;




@CrossOrigin(origins = "http://elwiam.github.io", allowedHeaders = "*")
@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    
    
    @Autowired
    private SpecialityRepository specialityRepository;
    
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
    @PostMapping("/reservation")
    
    public ResponseEntity<String> makeReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            // Extraire les paramètres de réservation de l'objet ReservationRequest
            String patientName = reservationRequest.getPatientName();
            String phoneNumber = reservationRequest.getPhoneNumber();
            int numPatients = reservationRequest.getNumPatients();
            int numBeds = reservationRequest.getNumBeds();
            String specialityName = reservationRequest.getSpecialityName(); // Récupération du nom de la spécialité
            Speciality speciality = specialityRepository.findByName(specialityName)
                .orElseThrow(() -> new IllegalArgumentException("Spécialité non trouvée avec nom: " + specialityName));

           String SpecialityName = speciality.getName(); // Récupération de l'ID de la spécialité

            // Utilisez specialityId dans votre code

            Long hospitalId = reservationRequest.getHospitalId();
            
            
               // Débogage : Afficher les valeurs extraites
            System.out.println("Patient Name: " + patientName);
            System.out.println("Phone Number: " + phoneNumber);
            System.out.println("Num Patients: " + numPatients);
            System.out.println("Num Beds: " + numBeds);
            System.out.println("Hospital ID: " + hospitalId);
            System.out.println("Speciality: " + speciality.getId());

            // Définir la spécialité dans ReservationRequest
            reservationRequest.setSpeciality(speciality);

            // Appeler la méthode createReservation dans ReservationService
            reservationService.createReservation(reservationRequest);

            return ResponseEntity.ok("La réservation a été effectuée avec succès.");
        } catch (Exception e) {
            e.printStackTrace(); // Affichez l'exception dans la console pour le débogage
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors de la réservation: " + e.getMessage());
        }
    }
}