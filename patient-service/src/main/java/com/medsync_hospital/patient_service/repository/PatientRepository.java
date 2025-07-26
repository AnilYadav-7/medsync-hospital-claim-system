package com.medsync_hospital.patient_service.repository;

import com.medsync_hospital.patient_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    Optional<Patient> findByEmail(String email);
    
    Optional<Patient> findByInsuranceNumber(String insuranceNumber);
    
    List<Patient> findByFirstNameContainingIgnoreCase(String firstName);
    
    List<Patient> findByLastNameContainingIgnoreCase(String lastName);
    
    @Query("SELECT p FROM Patient p WHERE " +
           "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Patient> searchPatients(@Param("searchTerm") String searchTerm);
    
    boolean existsByEmail(String email);
    
    boolean existsByInsuranceNumber(String insuranceNumber);
}