package com.medsync_hospital.claim_service.controller;

import com.medsync_hospital.claim_service.dto.ClaimRequestDTO;
import com.medsync_hospital.claim_service.dto.ClaimResponseDTO;
import com.medsync_hospital.claim_service.enums.ClaimStatus;
import com.medsync_hospital.claim_service.enums.ClaimType;
import com.medsync_hospital.claim_service.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    // Create Claim
    @PostMapping
    public ResponseEntity<ClaimResponseDTO> createClaim(@RequestBody ClaimRequestDTO claimRequestDTO) {
        try {
            ClaimResponseDTO response = claimService.createClaim(claimRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get All Claims
    @GetMapping
    public ResponseEntity<List<ClaimResponseDTO>> getAllClaims() {
        try {
            List<ClaimResponseDTO> claimList = claimService.getAllClaims();
            return ResponseEntity.ok(claimList);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get Claim by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClaimResponseDTO> getClaimById(@PathVariable Long id) {
        try {
            ClaimResponseDTO claim = claimService.getClaimById(id);
            return ResponseEntity.ok(claim);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Claim by Claim Number
    @GetMapping("/claim-number/{claimNumber}")
    public ResponseEntity<ClaimResponseDTO> getClaimByClaimNumber(@PathVariable String claimNumber) {
        try {
            ClaimResponseDTO claim = claimService.getClaimByClaimNumber(claimNumber);
            return ResponseEntity.ok(claim);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Claims by Patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByPatientId(@PathVariable Long patientId) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByPatientId(patientId);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Claims by Hospital ID
    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByHospitalId(@PathVariable Long hospitalId) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByHospitalId(hospitalId);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Claims by Insurance Company ID
    @GetMapping("/insurance-company/{insuranceCompanyId}")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByInsuranceCompanyId(@PathVariable Long insuranceCompanyId) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByInsuranceCompanyId(insuranceCompanyId);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Claims by Staff ID
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByStaffId(@PathVariable Long staffId) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByStaffId(staffId);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Claims by Type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByType(@PathVariable ClaimType type) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByType(type);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Claims by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByStatus(@PathVariable ClaimStatus status) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByStatus(status);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Claims by Claim Date Range
    @GetMapping("/claim-date-range")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByClaimDateRange(
            @RequestParam LocalDate startDate, 
            @RequestParam LocalDate endDate) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByClaimDateRange(startDate, endDate);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Claims by Admission Date Range
    @GetMapping("/admission-date-range")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByAdmissionDateRange(
            @RequestParam LocalDate startDate, 
            @RequestParam LocalDate endDate) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByAdmissionDateRange(startDate, endDate);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Claims by Discharge Date Range
    @GetMapping("/discharge-date-range")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByDischargeDateRange(
            @RequestParam LocalDate startDate, 
            @RequestParam LocalDate endDate) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByDischargeDateRange(startDate, endDate);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Claims by Total Amount Range
    @GetMapping("/total-amount")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByTotalAmountGreaterThanEqual(@RequestParam BigDecimal minAmount) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByTotalAmountGreaterThanEqual(minAmount);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Claims by Covered Amount Range
    @GetMapping("/covered-amount")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByCoveredAmountGreaterThanEqual(@RequestParam BigDecimal minAmount) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByCoveredAmountGreaterThanEqual(minAmount);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Claims by Patient Responsibility Range
    @GetMapping("/patient-responsibility")
    public ResponseEntity<List<ClaimResponseDTO>> getClaimsByPatientResponsibilityGreaterThanEqual(@RequestParam BigDecimal minAmount) {
        try {
            List<ClaimResponseDTO> claims = claimService.getClaimsByPatientResponsibilityGreaterThanEqual(minAmount);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Search Claims by Diagnosis
    @GetMapping("/search/diagnosis")
    public ResponseEntity<List<ClaimResponseDTO>> searchClaimsByDiagnosis(@RequestParam String diagnosis) {
        try {
            List<ClaimResponseDTO> claims = claimService.searchClaimsByDiagnosis(diagnosis);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Search Claims by Treatment
    @GetMapping("/search/treatment")
    public ResponseEntity<List<ClaimResponseDTO>> searchClaimsByTreatment(@RequestParam String treatment) {
        try {
            List<ClaimResponseDTO> claims = claimService.searchClaimsByTreatment(treatment);
            return ResponseEntity.ok(claims);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update Claim
    @PutMapping("/{id}")
    public ResponseEntity<ClaimResponseDTO> updateClaim(@PathVariable Long id, @RequestBody ClaimRequestDTO claimRequestDTO) {
        try {
            ClaimResponseDTO response = claimService.updateClaim(id, claimRequestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update Claim Status
    @PatchMapping("/{id}/status")
    public ResponseEntity<ClaimResponseDTO> updateClaimStatus(@PathVariable Long id, @RequestParam ClaimStatus status) {
        try {
            ClaimResponseDTO response = claimService.updateClaimStatus(id, status);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete Claim
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable Long id) {
        try {
            claimService.deleteClaim(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 