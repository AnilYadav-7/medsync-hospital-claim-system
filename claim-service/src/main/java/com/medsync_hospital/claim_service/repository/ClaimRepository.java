package com.medsync_hospital.claim_service.repository;

import com.medsync_hospital.claim_service.entity.Claim;
import com.medsync_hospital.claim_service.enums.ClaimStatus;
import com.medsync_hospital.claim_service.enums.ClaimType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    Optional<Claim> findByClaimNumber(String claimNumber);
    List<Claim> findByPatientId(Long patientId);
    List<Claim> findByHospitalId(Long hospitalId);
    List<Claim> findByInsuranceCompanyId(Long insuranceCompanyId);
    List<Claim> findByStaffId(Long staffId);
    List<Claim> findByType(ClaimType type);
    List<Claim> findByStatus(ClaimStatus status);
    List<Claim> findByClaimDateBetween(LocalDate startDate, LocalDate endDate);
    List<Claim> findByAdmissionDateBetween(LocalDate startDate, LocalDate endDate);
    List<Claim> findByDischargeDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT c FROM Claim c WHERE c.totalAmount >= :minAmount")
    List<Claim> findByTotalAmountGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);
    
    @Query("SELECT c FROM Claim c WHERE c.coveredAmount >= :minAmount")
    List<Claim> findByCoveredAmountGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);
    
    @Query("SELECT c FROM Claim c WHERE c.patientResponsibility >= :minAmount")
    List<Claim> findByPatientResponsibilityGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);
    
    @Query("SELECT c FROM Claim c WHERE c.diagnosis LIKE %:diagnosis%")
    List<Claim> findByDiagnosisContaining(@Param("diagnosis") String diagnosis);
    
    @Query("SELECT c FROM Claim c WHERE c.treatment LIKE %:treatment%")
    List<Claim> findByTreatmentContaining(@Param("treatment") String treatment);
    
    boolean existsByClaimNumber(String claimNumber);
} 