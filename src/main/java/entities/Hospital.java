package entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    private double latitude;

    private double longitude;
    

    @Column(name = "availablebeds")
    private Integer availableBeds;

    @Column(length = 100)
    private String address;
    
    @Column(name = "postalcode")
    private String postalCode;
    


    @ManyToMany
    @JoinTable(
        name = "hospital_speciality",
        joinColumns = @JoinColumn(name = "hospital_id"),
        inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private List<Speciality> specialities;
    
    @JsonIgnore // Ignorer la propriété "distance" lors de la sérialisation JSON
    private Double distance;

    public Hospital() {
        // Constructeur par défaut sans arguments
    }

    public Hospital(Long id) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.availableBeds = availableBeds;
        this.postalCode =postalCode;
    }

    public void addSpeciality(Speciality speciality) {
        specialities.add(speciality);
    }

    
    
    public boolean hasSpeciality(String speciality) {
        // Vérifier si l'hôpital a la spécialité donnée
        for (Speciality hospitalSpeciality : specialities) {
            if (hospitalSpeciality.getName().equalsIgnoreCase(speciality)) {
                return true;
            }
        }
        return false;
    }

    
    
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Integer getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(Integer availableBeds) {
        this.availableBeds = availableBeds;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    
    
    
    
}
