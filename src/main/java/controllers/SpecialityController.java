package controllers;

import entities.Hospital;
import entities.Speciality;
import services.HospitalService;
import services.SpecialityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://elwiam.github.io", allowedHeaders = "*")
@RestController
@RequestMapping("/specialities")
public class SpecialityController {

    @Autowired
    private SpecialityService specialityService;

    @Autowired
    private HospitalService hospitalService;
    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }
    @GetMapping
    public ResponseEntity<List<Speciality>> getAllSpecialities() {
        List<Speciality> specialities = specialityService.getSpecialities();
        return new ResponseEntity<>(specialities, HttpStatus.OK);
    }

    @GetMapping("/{specialityId}/hospitals")
    public ResponseEntity<List<Hospital>> getHospitalsBySpeciality(@PathVariable int specialityId) {
        List<Hospital> hospitals = hospitalService.getHospitalsBySpeciality(specialityId);
        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Speciality> addSpeciality(@RequestBody Speciality speciality) {
        Speciality addedSpeciality = specialityService.addSpeciality(speciality);
        return new ResponseEntity<>(addedSpeciality, HttpStatus.CREATED);
    }
}
