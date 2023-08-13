package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import entities.Hospital;

import java.util.List;

@Repository


public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("SELECT h FROM Hospital h JOIN h.specialities s WHERE s.id = :specialityId")
    List<Hospital> findBySpecialityId(Long specialityId);


   


}
