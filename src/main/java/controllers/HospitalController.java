package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import entities.Hospital;
import entities.Speciality;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import services.HospitalService;
import services.SpecialityService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.ui.Model;
import CustomError.HospitalNotFoundException;



@CrossOrigin(origins = "http://elwiam.github.io", allowedHeaders = "*")
@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;
    private final SpecialityService specialityService;

    @Autowired
    public HospitalController(HospitalService hospitalService, SpecialityService specialityService) {
        this.hospitalService = hospitalService;
        this.specialityService = specialityService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Hospital>> getAllHospitals() {
        Iterable<Hospital> hospitals = hospitalService.getAllHospitals();
        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable Long id) {
        Hospital hospital = hospitalService.getHospitalById(id);
        if (hospital != null) {
            return new ResponseEntity<>(hospital, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
  
    @PostMapping
    public ResponseEntity<Hospital> createHospital(@RequestBody Hospital hospital) {
        Hospital createdHospital = hospitalService.createHospital(hospital);
        return new ResponseEntity<>(createdHospital, HttpStatus.CREATED);
    }
  
    @PutMapping("/{id}")
    public ResponseEntity<Hospital> updateHospital(@PathVariable Long id, @RequestBody Hospital hospital) {
        Hospital existingHospital = hospitalService.getHospitalById(id);
        if (existingHospital != null) {
            hospital.setId(id);
            Hospital updatedHospital = hospitalService.updateHospital(id, hospital);
            return new ResponseEntity<>(updatedHospital, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
        Hospital existingHospital = hospitalService.getHospitalById(id);
        if (existingHospital != null) {
            hospitalService.deleteHospital(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{hospitalId}/specialities/{specialityId}")
    public ResponseEntity<Hospital> addSpecialityToHospital(
            @PathVariable Long hospitalId,
            @PathVariable Long specialityId
    ) {
        Optional<Hospital> optionalHospital = hospitalService.getHospital(hospitalId);
        Optional<Speciality> optionalSpeciality = specialityService.getSpecialityById(specialityId);

        if (optionalHospital.isPresent() && optionalSpeciality.isPresent()) {
            Hospital existingHospital = optionalHospital.get();
            Speciality speciality = optionalSpeciality.get();
            existingHospital.addSpeciality(speciality);
            hospitalService.updateHospital(hospitalId, existingHospital);
            return ResponseEntity.ok(existingHospital);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

  
    
    
    
    
    @PostMapping("/search")
    public String searchHospitals(@RequestParam("address") String address, @RequestParam("speciality") String speciality, Model model) {
        List<Hospital> hospitals = StreamSupport.stream(hospitalService.getAllHospitals().spliterator(), false)
                .collect(Collectors.toList());

        // Filtrer les hôpitaux par spécialité
        hospitals = hospitals.stream()
                .filter(hospital -> hospital.getSpecialities().stream()
                        .anyMatch(spec -> spec.getName().equalsIgnoreCase(speciality)))
                .collect(Collectors.toList());

        // Trier les hôpitaux par distance (ajoutez votre logique de calcul de distance ici)
        Collections.sort(hospitals, Comparator.comparingDouble(Hospital::getDistance));

        model.addAttribute("hospitals", hospitals);
        return "hospitalList";
    }
    
    
    @PutMapping("/{id}/beds")
  
    public ResponseEntity<Hospital> updateAvailableBeds(@PathVariable Long id, @RequestParam("numBeds") int numBeds) {
        Hospital existingHospital = hospitalService.getHospitalById(id);
        if (existingHospital != null) {
            existingHospital.setAvailableBeds(numBeds);
            Hospital updatedHospital = hospitalService.updateHospital(id, existingHospital);
            return new ResponseEntity<>(updatedHospital, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
     
    
}
