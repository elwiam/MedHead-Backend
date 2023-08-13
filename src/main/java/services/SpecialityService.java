package services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import entities.Speciality;
import repositories.SpecialityRepository;

@Service
public class SpecialityService {
    private SpecialityRepository specialityRepository;

    public SpecialityService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    public List<Speciality> getSpecialities() {
        return specialityRepository.findAll();
    }

    public Optional<Speciality> getSpecialityById(Long id) {
        return specialityRepository.findById(id);
    }

    public Speciality addSpeciality(Speciality speciality) {
        return specialityRepository.save(speciality);
    }
    

    
    
}
