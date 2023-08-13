package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.HospitalRepository;
import CustomError.HospitalNotFoundException;
import entities.Hospital;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import services.Coordinates;
import entities.Hospital;



@Service
public class HospitalService {
	@Autowired
    private HospitalRepository hospitalRepository;
    private NominatimService nominatimService;

    public HospitalService(HospitalRepository hospitalRepository, NominatimService nominatimService) {
        this.hospitalRepository = hospitalRepository;
        this.nominatimService = nominatimService;
    }

    public Optional<Hospital> getHospital(Long id) {
        return hospitalRepository.findById(id);
    }

    public List<Hospital> getHospitalsBySpeciality(int specialityId) {
        return hospitalRepository.findBySpecialityId((long) specialityId);
    }

    public Hospital getHospitalById(Long id) throws HospitalNotFoundException {
        Optional<Hospital> optionalHospital = hospitalRepository.findById(id);
        if (optionalHospital.isPresent()) {
            return optionalHospital.get();
        } else {
            throw new HospitalNotFoundException("Hospital not found with id: " + id);
        }
    }

    public Hospital createHospital(Hospital hospital) {
        String address = hospital.getAddress();
        String postalCode = hospital.getPostalCode(); // Récupérer le code postal de l'hôpital
        Coordinates coordinates = geocodeAddress(address);
        if (coordinates != null) {
            hospital.setLatitude(coordinates.getLatitude());
            hospital.setLongitude(coordinates.getLongitude());
        } else {
            // Adresse introuvable
        }
        hospital.setPostalCode(postalCode); // Définir le code postal de l'hôpital
        return hospitalRepository.save(hospital);
    }

    public Hospital updateHospital(Long id, Hospital hospital) throws HospitalNotFoundException {
        Optional<Hospital> optionalHospital = hospitalRepository.findById(id);
        if (optionalHospital.isPresent()) {
            Hospital existingHospital = optionalHospital.get();
            existingHospital.setName(hospital.getName());
            existingHospital.setLatitude(hospital.getLatitude());
            existingHospital.setLongitude(hospital.getLongitude());
            existingHospital.setAvailableBeds(hospital.getAvailableBeds());
            existingHospital.setSpecialities(hospital.getSpecialities());
            existingHospital.setPostalCode(hospital.getPostalCode()); // Mettre à jour le code postal de l'hôpital
            
            return hospitalRepository.save(existingHospital);
        } else {
            throw new HospitalNotFoundException("Hospital not found with id: " + id);
        }
    }


    public Iterable<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public void deleteHospital(Long id) throws HospitalNotFoundException {
        Optional<Hospital> optionalHospital = hospitalRepository.findById(id);
        if (optionalHospital.isPresent()) {
            hospitalRepository.deleteById(id);
        } else {
            throw new HospitalNotFoundException("Hospital not found with id: " + id);
        }
    }

    private Coordinates geocodeAddress(String address) {
        return nominatimService.geocodeAddress(address);
    }

	public List<Hospital> getNearestHospitals(double latitude, double longitude, Long speciality) {
		// TODO Auto-generated method stub
		return null;
	}


}
