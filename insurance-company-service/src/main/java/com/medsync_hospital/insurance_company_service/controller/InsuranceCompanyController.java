package com.medsync_hospital.insurance_company_service.controller;

import com.medsync_hospital.insurance_company_service.dto.InsuranceCompanyRequestDTO;
import com.medsync_hospital.insurance_company_service.dto.InsuranceCompanyResponseDTO;
import com.medsync_hospital.insurance_company_service.enums.InsuranceCompanyStatus;
import com.medsync_hospital.insurance_company_service.enums.InsuranceCompanyType;
import com.medsync_hospital.insurance_company_service.service.InsuranceCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance-companies")
public class InsuranceCompanyController {

    @Autowired
    private InsuranceCompanyService insuranceCompanyService;

    // Create Insurance Company
    @PostMapping
    public ResponseEntity<InsuranceCompanyResponseDTO> createInsuranceCompany(@RequestBody InsuranceCompanyRequestDTO insuranceCompanyRequestDTO) {
        try {
            InsuranceCompanyResponseDTO response = insuranceCompanyService.createInsuranceCompany(insuranceCompanyRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get All Insurance Companies
    @GetMapping
    public ResponseEntity<List<InsuranceCompanyResponseDTO>> getAllInsuranceCompanies() {
        try {
            List<InsuranceCompanyResponseDTO> insuranceCompanyList = insuranceCompanyService.getAllInsuranceCompanies();
            return ResponseEntity.ok(insuranceCompanyList);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get Insurance Company by ID
    @GetMapping("/{id}")
    public ResponseEntity<InsuranceCompanyResponseDTO> getInsuranceCompanyById(@PathVariable Long id) {
        try {
            InsuranceCompanyResponseDTO insuranceCompany = insuranceCompanyService.getInsuranceCompanyById(id);
            return ResponseEntity.ok(insuranceCompany);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Insurance Company by Code
    @GetMapping("/code/{code}")
    public ResponseEntity<InsuranceCompanyResponseDTO> getInsuranceCompanyByCode(@PathVariable String code) {
        try {
            InsuranceCompanyResponseDTO insuranceCompany = insuranceCompanyService.getInsuranceCompanyByCode(code);
            return ResponseEntity.ok(insuranceCompany);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Insurance Company by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<InsuranceCompanyResponseDTO> getInsuranceCompanyByEmail(@PathVariable String email) {
        try {
            InsuranceCompanyResponseDTO insuranceCompany = insuranceCompanyService.getInsuranceCompanyByEmail(email);
            return ResponseEntity.ok(insuranceCompany);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Insurance Company by License Number
    @GetMapping("/license/{licenseNumber}")
    public ResponseEntity<InsuranceCompanyResponseDTO> getInsuranceCompanyByLicenseNumber(@PathVariable String licenseNumber) {
        try {
            InsuranceCompanyResponseDTO insuranceCompany = insuranceCompanyService.getInsuranceCompanyByLicenseNumber(licenseNumber);
            return ResponseEntity.ok(insuranceCompany);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Search Insurance Companies by Name
    @GetMapping("/search")
    public ResponseEntity<List<InsuranceCompanyResponseDTO>> searchInsuranceCompaniesByName(@RequestParam String name) {
        try {
            List<InsuranceCompanyResponseDTO> insuranceCompanies = insuranceCompanyService.searchInsuranceCompaniesByName(name);
            return ResponseEntity.ok(insuranceCompanies);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Insurance Companies by Type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<InsuranceCompanyResponseDTO>> getInsuranceCompaniesByType(@PathVariable InsuranceCompanyType type) {
        try {
            List<InsuranceCompanyResponseDTO> insuranceCompanies = insuranceCompanyService.getInsuranceCompaniesByType(type);
            return ResponseEntity.ok(insuranceCompanies);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Insurance Companies by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<InsuranceCompanyResponseDTO>> getInsuranceCompaniesByStatus(@PathVariable InsuranceCompanyStatus status) {
        try {
            List<InsuranceCompanyResponseDTO> insuranceCompanies = insuranceCompanyService.getInsuranceCompaniesByStatus(status);
            return ResponseEntity.ok(insuranceCompanies);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Insurance Companies by City
    @GetMapping("/city/{city}")
    public ResponseEntity<List<InsuranceCompanyResponseDTO>> getInsuranceCompaniesByCity(@PathVariable String city) {
        try {
            List<InsuranceCompanyResponseDTO> insuranceCompanies = insuranceCompanyService.getInsuranceCompaniesByCity(city);
            return ResponseEntity.ok(insuranceCompanies);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Insurance Companies by State
    @GetMapping("/state/{state}")
    public ResponseEntity<List<InsuranceCompanyResponseDTO>> getInsuranceCompaniesByState(@PathVariable String state) {
        try {
            List<InsuranceCompanyResponseDTO> insuranceCompanies = insuranceCompanyService.getInsuranceCompaniesByState(state);
            return ResponseEntity.ok(insuranceCompanies);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Insurance Companies by Country
    @GetMapping("/country/{country}")
    public ResponseEntity<List<InsuranceCompanyResponseDTO>> getInsuranceCompaniesByCountry(@PathVariable String country) {
        try {
            List<InsuranceCompanyResponseDTO> insuranceCompanies = insuranceCompanyService.getInsuranceCompaniesByCountry(country);
            return ResponseEntity.ok(insuranceCompanies);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Insurance Companies by Network Hospital
    @GetMapping("/network-hospital")
    public ResponseEntity<List<InsuranceCompanyResponseDTO>> getInsuranceCompaniesByNetworkHospital(@RequestParam String hospitalName) {
        try {
            List<InsuranceCompanyResponseDTO> insuranceCompanies = insuranceCompanyService.getInsuranceCompaniesByNetworkHospital(hospitalName);
            return ResponseEntity.ok(insuranceCompanies);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update Insurance Company
    @PutMapping("/{id}")
    public ResponseEntity<InsuranceCompanyResponseDTO> updateInsuranceCompany(@PathVariable Long id, @RequestBody InsuranceCompanyRequestDTO insuranceCompanyRequestDTO) {
        try {
            InsuranceCompanyResponseDTO response = insuranceCompanyService.updateInsuranceCompany(id, insuranceCompanyRequestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update Insurance Company Status
    @PatchMapping("/{id}/status")
    public ResponseEntity<InsuranceCompanyResponseDTO> updateInsuranceCompanyStatus(@PathVariable Long id, @RequestParam InsuranceCompanyStatus status) {
        try {
            InsuranceCompanyResponseDTO response = insuranceCompanyService.updateInsuranceCompanyStatus(id, status);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete Insurance Company
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsuranceCompany(@PathVariable Long id) {
        try {
            insuranceCompanyService.deleteInsuranceCompany(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 