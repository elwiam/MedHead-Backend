package entities;


import javax.persistence.*;
import java.time.LocalDateTime;

import entities.Hospital;
import entities.Speciality;

@Entity
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    
    
    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "speciality_id", nullable = false)
    private Speciality speciality;

    @Column(name = "num_patients", nullable = false)
    private int numPatients;

    private int numBeds;

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;

    
    public Reservation() {
        // Constructor without initializing speciality
    }
    
    public Reservation(String patientName, String phoneNumber, Hospital hospital, Speciality speciality, int numPatients, LocalDateTime reservationDate) {
        this.patientName = patientName;
        this.phoneNumber = phoneNumber;
        this.hospital = hospital;
        this.speciality = speciality ; // Use provided speciality if not null, otherwise initialize with a new instance
        this.numPatients = numPatients;
        this.numBeds = 0; // Initialiser le nombre de lits à 0
        this.reservationDate = reservationDate;
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Hospital getHospital() {
        
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public int getNumPatients() {
        return numPatients;
    }

    public void setNumPatients(int numPatients) {
        this.numPatients = numPatients;
    }

    public int getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }
}
