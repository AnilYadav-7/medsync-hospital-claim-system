package com.medsync_hospital.patient_service.repository;

import com.medsync_hospital.patient_service.entity.Patient;
import com.medsync_hospital.patient_service.enums.BloodGroup;
import com.medsync_hospital.patient_service.enums.Gender;
import com.medsync_hospital.patient_service.enums.PatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByPhone(String phone);
    List<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    List<Patient> findByGender(Gender gender);
    List<Patient> findByBloodGroup(BloodGroup bloodGroup);
    List<Patient> findByStatus(PatientStatus status);
    List<Patient> findByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
    List<Patient> findByCity(String city);
    List<Patient> findByState(String state);
    
    @Query("SELECT p FROM Patient p WHERE p.firstName LIKE %:name% OR p.lastName LIKE %:name%")
    List<Patient> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT p FROM Patient p WHERE p.emergencyContactPhone = :phone")
    List<Patient> findByEmergencyContactPhone(@Param("phone") String phone);
    
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
} 