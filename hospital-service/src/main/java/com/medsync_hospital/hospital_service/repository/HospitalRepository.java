package com.medsync_hospital.hospital_service.repository;

import com.medsync_hospital.hospital_service.entity.Hospital;
import com.medsync_hospital.hospital_service.enums.HospitalStatus;
import com.medsync_hospital.hospital_service.enums.HospitalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByCode(String code);
    Optional<Hospital> findByEmail(String email);
    Optional<Hospital> findByPhone(String phone);
    List<Hospital> findByNameContainingIgnoreCase(String name);
    List<Hospital> findByType(HospitalType type);
    List<Hospital> findByStatus(HospitalStatus status);
    List<Hospital> findByCity(String city);
    List<Hospital> findByState(String state);
    List<Hospital> findByCountry(String country);
    
    @Query("SELECT h FROM Hospital h WHERE h.availableBeds > 0")
    List<Hospital> findHospitalsWithAvailableBeds();
    
    @Query("SELECT h FROM Hospital h WHERE h.totalBeds >= :minBeds")
    List<Hospital> findHospitalsWithMinimumBeds(@Param("minBeds") Integer minBeds);
    
    @Query("SELECT h FROM Hospital h WHERE h.totalDoctors >= :minDoctors")
    List<Hospital> findHospitalsWithMinimumDoctors(@Param("minDoctors") Integer minDoctors);
    
    boolean existsByCode(String code);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
} 