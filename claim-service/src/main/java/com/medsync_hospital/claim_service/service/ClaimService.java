package com.medsync_hospital.claim_service.service;

import com.medsync_hospital.claim_service.dto.ClaimRequestDTO;
import com.medsync_hospital.claim_service.dto.ClaimResponseDTO;
import com.medsync_hospital.claim_service.entity.Claim;
import com.medsync_hospital.claim_service.enums.ClaimStatus;
import com.medsync_hospital.claim_service.enums.ClaimType;
import com.medsync_hospital.claim_service.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    // Create Claim
    public ClaimResponseDTO createClaim(ClaimRequestDTO claimRequestDTO) {
        // Basic validation
        if (claimRequestDTO == null) {
            throw new RuntimeException("Claim data is required");
        }

        // Check if claim number already exists
        if (claimRepository.existsByClaimNumber(claimRequestDTO.getClaimNumber())) {
            throw new RuntimeException("Claim number already exists");
        }

        // Validate amounts
        if (claimRequestDTO.getTotalAmount() == null || claimRequestDTO.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Total amount must be greater than zero");
        }

        if (claimRequestDTO.getCoveredAmount() == null || claimRequestDTO.getCoveredAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Covered amount cannot be negative");
        }

        if (claimRequestDTO.getPatientResponsibility() == null || claimRequestDTO.getPatientResponsibility().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Patient responsibility cannot be negative");
        }

        // Validate that covered amount + patient responsibility equals total amount
        BigDecimal calculatedTotal = claimRequestDTO.getCoveredAmount().add(claimRequestDTO.getPatientResponsibility());
        if (calculatedTotal.compareTo(claimRequestDTO.getTotalAmount()) != 0) {
            throw new RuntimeException("Covered amount + Patient responsibility must equal total amount");
        }

        // Create new claim
        Claim claim = new Claim();
        claim.setClaimNumber(claimRequestDTO.getClaimNumber());
        claim.setPatientId(claimRequestDTO.getPatientId());
        claim.setHospitalId(claimRequestDTO.getHospitalId());
        claim.setInsuranceCompanyId(claimRequestDTO.getInsuranceCompanyId());
        claim.setStaffId(claimRequestDTO.getStaffId());
        claim.setType(claimRequestDTO.getType());
        claim.setStatus(claimRequestDTO.getStatus() != null ? claimRequestDTO.getStatus() : ClaimStatus.PENDING);
        claim.setAdmissionDate(claimRequestDTO.getAdmissionDate());
        claim.setDischargeDate(claimRequestDTO.getDischargeDate());
        claim.setDiagnosis(claimRequestDTO.getDiagnosis());
        claim.setTreatment(claimRequestDTO.getTreatment());
        claim.setTotalAmount(claimRequestDTO.getTotalAmount());
        claim.setCoveredAmount(claimRequestDTO.getCoveredAmount());
        claim.setPatientResponsibility(claimRequestDTO.getPatientResponsibility());
        claim.setDescription(claimRequestDTO.getDescription());
        claim.setDocuments(claimRequestDTO.getDocuments());
        claim.setNotes(claimRequestDTO.getNotes());
        claim.setRejectionReason(claimRequestDTO.getRejectionReason());
        claim.setClaimDate(claimRequestDTO.getClaimDate() != null ? claimRequestDTO.getClaimDate() : LocalDate.now());
        claim.setProcessedDate(claimRequestDTO.getProcessedDate());
        claim.setApprovedDate(claimRequestDTO.getApprovedDate());
        claim.setRejectedDate(claimRequestDTO.getRejectedDate());
        claim.setPaidDate(claimRequestDTO.getPaidDate());

        Claim savedClaim = claimRepository.save(claim);
        return convertToResponseDTO(savedClaim);
    }

    // Get All Claims
    public List<ClaimResponseDTO> getAllClaims() {
        List<Claim> claimList = claimRepository.findAll();
        return claimList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claim by ID
    public ClaimResponseDTO getClaimById(Long id) {
        if (id == null) {
            throw new RuntimeException("Claim ID is required");
        }

        Optional<Claim> claim = claimRepository.findById(id);
        if (claim.isEmpty()) {
            throw new RuntimeException("Claim not found");
        }

        return convertToResponseDTO(claim.get());
    }

    // Get Claim by Claim Number
    public ClaimResponseDTO getClaimByClaimNumber(String claimNumber) {
        if (claimNumber == null || claimNumber.trim().isEmpty()) {
            throw new RuntimeException("Claim number is required");
        }

        Optional<Claim> claim = claimRepository.findByClaimNumber(claimNumber);
        if (claim.isEmpty()) {
            throw new RuntimeException("Claim not found");
        }

        return convertToResponseDTO(claim.get());
    }

    // Get Claims by Patient ID
    public List<ClaimResponseDTO> getClaimsByPatientId(Long patientId) {
        if (patientId == null) {
            throw new RuntimeException("Patient ID is required");
        }

        List<Claim> claims = claimRepository.findByPatientId(patientId);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claims by Hospital ID
    public List<ClaimResponseDTO> getClaimsByHospitalId(Long hospitalId) {
        if (hospitalId == null) {
            throw new RuntimeException("Hospital ID is required");
        }

        List<Claim> claims = claimRepository.findByHospitalId(hospitalId);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claims by Insurance Company ID
    public List<ClaimResponseDTO> getClaimsByInsuranceCompanyId(Long insuranceCompanyId) {
        if (insuranceCompanyId == null) {
            throw new RuntimeException("Insurance company ID is required");
        }

        List<Claim> claims = claimRepository.findByInsuranceCompanyId(insuranceCompanyId);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claims by Staff ID
    public List<ClaimResponseDTO> getClaimsByStaffId(Long staffId) {
        if (staffId == null) {
            throw new RuntimeException("Staff ID is required");
        }

        List<Claim> claims = claimRepository.findByStaffId(staffId);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claims by Type
    public List<ClaimResponseDTO> getClaimsByType(ClaimType type) {
        if (type == null) {
            throw new RuntimeException("Claim type is required");
        }

        List<Claim> claims = claimRepository.findByType(type);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claims by Status
    public List<ClaimResponseDTO> getClaimsByStatus(ClaimStatus status) {
        if (status == null) {
            throw new RuntimeException("Claim status is required");
        }

        List<Claim> claims = claimRepository.findByStatus(status);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claims by Claim Date Range
    public List<ClaimResponseDTO> getClaimsByClaimDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start date and end date are required");
        }

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date must be before end date");
        }

        List<Claim> claims = claimRepository.findByClaimDateBetween(startDate, endDate);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claims by Admission Date Range
    public List<ClaimResponseDTO> getClaimsByAdmissionDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start date and end date are required");
        }

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date must be before end date");
        }

        List<Claim> claims = claimRepository.findByAdmissionDateBetween(startDate, endDate);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claims by Discharge Date Range
    public List<ClaimResponseDTO> getClaimsByDischargeDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start date and end date are required");
        }

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date must be before end date");
        }

        List<Claim> claims = claimRepository.findByDischargeDateBetween(startDate, endDate);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claims by Total Amount Range
    public List<ClaimResponseDTO> getClaimsByTotalAmountGreaterThanEqual(BigDecimal minAmount) {
        if (minAmount == null || minAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Minimum amount must be a positive number");
        }

        List<Claim> claims = claimRepository.findByTotalAmountGreaterThanEqual(minAmount);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claims by Covered Amount Range
    public List<ClaimResponseDTO> getClaimsByCoveredAmountGreaterThanEqual(BigDecimal minAmount) {
        if (minAmount == null || minAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Minimum amount must be a positive number");
        }

        List<Claim> claims = claimRepository.findByCoveredAmountGreaterThanEqual(minAmount);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Claims by Patient Responsibility Range
    public List<ClaimResponseDTO> getClaimsByPatientResponsibilityGreaterThanEqual(BigDecimal minAmount) {
        if (minAmount == null || minAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Minimum amount must be a positive number");
        }

        List<Claim> claims = claimRepository.findByPatientResponsibilityGreaterThanEqual(minAmount);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Search Claims by Diagnosis
    public List<ClaimResponseDTO> searchClaimsByDiagnosis(String diagnosis) {
        if (diagnosis == null || diagnosis.trim().isEmpty()) {
            throw new RuntimeException("Diagnosis is required");
        }

        List<Claim> claims = claimRepository.findByDiagnosisContaining(diagnosis);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Search Claims by Treatment
    public List<ClaimResponseDTO> searchClaimsByTreatment(String treatment) {
        if (treatment == null || treatment.trim().isEmpty()) {
            throw new RuntimeException("Treatment is required");
        }

        List<Claim> claims = claimRepository.findByTreatmentContaining(treatment);
        return claims.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Update Claim
    public ClaimResponseDTO updateClaim(Long id, ClaimRequestDTO claimRequestDTO) {
        if (id == null) {
            throw new RuntimeException("Claim ID is required");
        }

        if (claimRequestDTO == null) {
            throw new RuntimeException("Claim data is required");
        }

        Optional<Claim> existingClaim = claimRepository.findById(id);
        if (existingClaim.isEmpty()) {
            throw new RuntimeException("Claim not found");
        }

        Claim claim = existingClaim.get();

        // Check if claim number is being changed and if it already exists
        if (!claim.getClaimNumber().equals(claimRequestDTO.getClaimNumber()) && 
            claimRepository.existsByClaimNumber(claimRequestDTO.getClaimNumber())) {
            throw new RuntimeException("Claim number already exists");
        }

        // Validate amounts
        if (claimRequestDTO.getTotalAmount() != null && claimRequestDTO.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Total amount must be greater than zero");
        }

        if (claimRequestDTO.getCoveredAmount() != null && claimRequestDTO.getCoveredAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Covered amount cannot be negative");
        }

        if (claimRequestDTO.getPatientResponsibility() != null && claimRequestDTO.getPatientResponsibility().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Patient responsibility cannot be negative");
        }

        // Update claim fields
        if (claimRequestDTO.getClaimNumber() != null) {
            claim.setClaimNumber(claimRequestDTO.getClaimNumber());
        }
        if (claimRequestDTO.getPatientId() != null) {
            claim.setPatientId(claimRequestDTO.getPatientId());
        }
        if (claimRequestDTO.getHospitalId() != null) {
            claim.setHospitalId(claimRequestDTO.getHospitalId());
        }
        if (claimRequestDTO.getInsuranceCompanyId() != null) {
            claim.setInsuranceCompanyId(claimRequestDTO.getInsuranceCompanyId());
        }
        if (claimRequestDTO.getStaffId() != null) {
            claim.setStaffId(claimRequestDTO.getStaffId());
        }
        if (claimRequestDTO.getType() != null) {
            claim.setType(claimRequestDTO.getType());
        }
        if (claimRequestDTO.getStatus() != null) {
            claim.setStatus(claimRequestDTO.getStatus());
        }
        if (claimRequestDTO.getAdmissionDate() != null) {
            claim.setAdmissionDate(claimRequestDTO.getAdmissionDate());
        }
        if (claimRequestDTO.getDischargeDate() != null) {
            claim.setDischargeDate(claimRequestDTO.getDischargeDate());
        }
        if (claimRequestDTO.getDiagnosis() != null) {
            claim.setDiagnosis(claimRequestDTO.getDiagnosis());
        }
        if (claimRequestDTO.getTreatment() != null) {
            claim.setTreatment(claimRequestDTO.getTreatment());
        }
        if (claimRequestDTO.getTotalAmount() != null) {
            claim.setTotalAmount(claimRequestDTO.getTotalAmount());
        }
        if (claimRequestDTO.getCoveredAmount() != null) {
            claim.setCoveredAmount(claimRequestDTO.getCoveredAmount());
        }
        if (claimRequestDTO.getPatientResponsibility() != null) {
            claim.setPatientResponsibility(claimRequestDTO.getPatientResponsibility());
        }
        if (claimRequestDTO.getDescription() != null) {
            claim.setDescription(claimRequestDTO.getDescription());
        }
        if (claimRequestDTO.getDocuments() != null) {
            claim.setDocuments(claimRequestDTO.getDocuments());
        }
        if (claimRequestDTO.getNotes() != null) {
            claim.setNotes(claimRequestDTO.getNotes());
        }
        if (claimRequestDTO.getRejectionReason() != null) {
            claim.setRejectionReason(claimRequestDTO.getRejectionReason());
        }
        if (claimRequestDTO.getClaimDate() != null) {
            claim.setClaimDate(claimRequestDTO.getClaimDate());
        }
        if (claimRequestDTO.getProcessedDate() != null) {
            claim.setProcessedDate(claimRequestDTO.getProcessedDate());
        }
        if (claimRequestDTO.getApprovedDate() != null) {
            claim.setApprovedDate(claimRequestDTO.getApprovedDate());
        }
        if (claimRequestDTO.getRejectedDate() != null) {
            claim.setRejectedDate(claimRequestDTO.getRejectedDate());
        }
        if (claimRequestDTO.getPaidDate() != null) {
            claim.setPaidDate(claimRequestDTO.getPaidDate());
        }

        Claim updatedClaim = claimRepository.save(claim);
        return convertToResponseDTO(updatedClaim);
    }

    // Update Claim Status
    public ClaimResponseDTO updateClaimStatus(Long id, ClaimStatus status) {
        if (id == null) {
            throw new RuntimeException("Claim ID is required");
        }

        if (status == null) {
            throw new RuntimeException("Status is required");
        }

        Optional<Claim> existingClaim = claimRepository.findById(id);
        if (existingClaim.isEmpty()) {
            throw new RuntimeException("Claim not found");
        }

        Claim claim = existingClaim.get();
        claim.setStatus(status);

        // Update relevant dates based on status
        if (status == ClaimStatus.UNDER_REVIEW) {
            claim.setProcessedDate(LocalDate.now());
        } else if (status == ClaimStatus.APPROVED) {
            claim.setApprovedDate(LocalDate.now());
        } else if (status == ClaimStatus.REJECTED) {
            claim.setRejectedDate(LocalDate.now());
        } else if (status == ClaimStatus.PAID) {
            claim.setPaidDate(LocalDate.now());
        }

        Claim updatedClaim = claimRepository.save(claim);
        return convertToResponseDTO(updatedClaim);
    }

    // Delete Claim
    public void deleteClaim(Long id) {
        if (id == null) {
            throw new RuntimeException("Claim ID is required");
        }

        Optional<Claim> claim = claimRepository.findById(id);
        if (claim.isEmpty()) {
            throw new RuntimeException("Claim not found");
        }

        claimRepository.deleteById(id);
    }

    // Convert Entity to Response DTO
    private ClaimResponseDTO convertToResponseDTO(Claim claim) {
        ClaimResponseDTO response = new ClaimResponseDTO();
        response.setId(claim.getId());
        response.setClaimNumber(claim.getClaimNumber());
        response.setPatientId(claim.getPatientId());
        response.setHospitalId(claim.getHospitalId());
        response.setInsuranceCompanyId(claim.getInsuranceCompanyId());
        response.setStaffId(claim.getStaffId());
        response.setType(claim.getType());
        response.setStatus(claim.getStatus());
        response.setAdmissionDate(claim.getAdmissionDate());
        response.setDischargeDate(claim.getDischargeDate());
        response.setDiagnosis(claim.getDiagnosis());
        response.setTreatment(claim.getTreatment());
        response.setTotalAmount(claim.getTotalAmount());
        response.setCoveredAmount(claim.getCoveredAmount());
        response.setPatientResponsibility(claim.getPatientResponsibility());
        response.setDescription(claim.getDescription());
        response.setDocuments(claim.getDocuments());
        response.setNotes(claim.getNotes());
        response.setRejectionReason(claim.getRejectionReason());
        response.setClaimDate(claim.getClaimDate());
        response.setProcessedDate(claim.getProcessedDate());
        response.setApprovedDate(claim.getApprovedDate());
        response.setRejectedDate(claim.getRejectedDate());
        response.setPaidDate(claim.getPaidDate());
        response.setCreatedAt(claim.getCreatedAt());
        response.setUpdatedAt(claim.getUpdatedAt());
        
        return response;
    }
} 