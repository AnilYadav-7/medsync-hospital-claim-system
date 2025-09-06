package com.medsync_hospital.insurance_company_service.service;

import com.medsync_hospital.insurance_company_service.dto.InsuranceCompanyRequestDTO;
import com.medsync_hospital.insurance_company_service.dto.InsuranceCompanyResponseDTO;
import com.medsync_hospital.insurance_company_service.entity.InsuranceCompany;
import com.medsync_hospital.insurance_company_service.enums.InsuranceCompanyStatus;
import com.medsync_hospital.insurance_company_service.enums.InsuranceCompanyType;
import com.medsync_hospital.insurance_company_service.repository.InsuranceCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InsuranceCompanyService {

    @Autowired
    private InsuranceCompanyRepository insuranceCompanyRepository;

    // Create Insurance Company
    public InsuranceCompanyResponseDTO createInsuranceCompany(InsuranceCompanyRequestDTO insuranceCompanyRequestDTO) {
        // Basic validation
        if (insuranceCompanyRequestDTO == null) {
            throw new RuntimeException("Insurance company data is required");
        }

        // Check if code already exists
        if (insuranceCompanyRepository.existsByCode(insuranceCompanyRequestDTO.getCode())) {
            throw new RuntimeException("Insurance company code already exists");
        }

        // Check if email already exists
        if (insuranceCompanyRepository.existsByEmail(insuranceCompanyRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check if phone already exists
        Optional<InsuranceCompany> existingPhone = insuranceCompanyRepository.findByPhone(insuranceCompanyRequestDTO.getPhone());
        if (existingPhone.isPresent()) {
            throw new RuntimeException("Phone already exists");
        }

        // Check if license number already exists
        if (insuranceCompanyRepository.existsByLicenseNumber(insuranceCompanyRequestDTO.getLicenseNumber())) {
            throw new RuntimeException("License number already exists");
        }

        // Check if tax ID already exists
        if (insuranceCompanyRepository.existsByTaxId(insuranceCompanyRequestDTO.getTaxId())) {
            throw new RuntimeException("Tax ID already exists");
        }

        // Create new insurance company
        InsuranceCompany insuranceCompany = new InsuranceCompany();
        insuranceCompany.setName(insuranceCompanyRequestDTO.getName());
        insuranceCompany.setCode(insuranceCompanyRequestDTO.getCode());
        insuranceCompany.setType(insuranceCompanyRequestDTO.getType());
        insuranceCompany.setAddress(insuranceCompanyRequestDTO.getAddress());
        insuranceCompany.setCity(insuranceCompanyRequestDTO.getCity());
        insuranceCompany.setState(insuranceCompanyRequestDTO.getState());
        insuranceCompany.setZipCode(insuranceCompanyRequestDTO.getZipCode());
        insuranceCompany.setCountry(insuranceCompanyRequestDTO.getCountry());
        insuranceCompany.setPhone(insuranceCompanyRequestDTO.getPhone());
        insuranceCompany.setEmail(insuranceCompanyRequestDTO.getEmail());
        insuranceCompany.setWebsite(insuranceCompanyRequestDTO.getWebsite());
        insuranceCompany.setContactPerson(insuranceCompanyRequestDTO.getContactPerson());
        insuranceCompany.setContactPhone(insuranceCompanyRequestDTO.getContactPhone());
        insuranceCompany.setContactEmail(insuranceCompanyRequestDTO.getContactEmail());
        insuranceCompany.setDescription(insuranceCompanyRequestDTO.getDescription());
        insuranceCompany.setLicenseNumber(insuranceCompanyRequestDTO.getLicenseNumber());
        insuranceCompany.setTaxId(insuranceCompanyRequestDTO.getTaxId());
        insuranceCompany.setCoverageDetails(insuranceCompanyRequestDTO.getCoverageDetails());
        insuranceCompany.setPolicyTypes(insuranceCompanyRequestDTO.getPolicyTypes());
        insuranceCompany.setNetworkHospitals(insuranceCompanyRequestDTO.getNetworkHospitals());
        insuranceCompany.setClaimProcess(insuranceCompanyRequestDTO.getClaimProcess());
        insuranceCompany.setTermsAndConditions(insuranceCompanyRequestDTO.getTermsAndConditions());
        insuranceCompany.setStatus(insuranceCompanyRequestDTO.getStatus() != null ? insuranceCompanyRequestDTO.getStatus() : InsuranceCompanyStatus.ACTIVE);

        InsuranceCompany savedInsuranceCompany = insuranceCompanyRepository.save(insuranceCompany);
        return convertToResponseDTO(savedInsuranceCompany);
    }

    // Get All Insurance Companies
    public List<InsuranceCompanyResponseDTO> getAllInsuranceCompanies() {
        List<InsuranceCompany> insuranceCompanyList = insuranceCompanyRepository.findAll();
        return insuranceCompanyList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Insurance Company by ID
    public InsuranceCompanyResponseDTO getInsuranceCompanyById(Long id) {
        if (id == null) {
            throw new RuntimeException("Insurance company ID is required");
        }

        Optional<InsuranceCompany> insuranceCompany = insuranceCompanyRepository.findById(id);
        if (insuranceCompany.isEmpty()) {
            throw new RuntimeException("Insurance company not found");
        }

        return convertToResponseDTO(insuranceCompany.get());
    }

    // Get Insurance Company by Code
    public InsuranceCompanyResponseDTO getInsuranceCompanyByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new RuntimeException("Insurance company code is required");
        }

        Optional<InsuranceCompany> insuranceCompany = insuranceCompanyRepository.findByCode(code);
        if (insuranceCompany.isEmpty()) {
            throw new RuntimeException("Insurance company not found");
        }

        return convertToResponseDTO(insuranceCompany.get());
    }

    // Get Insurance Company by Email
    public InsuranceCompanyResponseDTO getInsuranceCompanyByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        Optional<InsuranceCompany> insuranceCompany = insuranceCompanyRepository.findByEmail(email);
        if (insuranceCompany.isEmpty()) {
            throw new RuntimeException("Insurance company not found");
        }

        return convertToResponseDTO(insuranceCompany.get());
    }

    // Get Insurance Company by License Number
    public InsuranceCompanyResponseDTO getInsuranceCompanyByLicenseNumber(String licenseNumber) {
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            throw new RuntimeException("License number is required");
        }

        Optional<InsuranceCompany> insuranceCompany = insuranceCompanyRepository.findByLicenseNumber(licenseNumber);
        if (insuranceCompany.isEmpty()) {
            throw new RuntimeException("Insurance company not found");
        }

        return convertToResponseDTO(insuranceCompany.get());
    }

    // Search Insurance Companies by Name
    public List<InsuranceCompanyResponseDTO> searchInsuranceCompaniesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("Name is required");
        }

        List<InsuranceCompany> insuranceCompanies = insuranceCompanyRepository.findByNameContainingIgnoreCase(name);
        return insuranceCompanies.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Insurance Companies by Type
    public List<InsuranceCompanyResponseDTO> getInsuranceCompaniesByType(InsuranceCompanyType type) {
        if (type == null) {
            throw new RuntimeException("Insurance company type is required");
        }

        List<InsuranceCompany> insuranceCompanies = insuranceCompanyRepository.findByType(type);
        return insuranceCompanies.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Insurance Companies by Status
    public List<InsuranceCompanyResponseDTO> getInsuranceCompaniesByStatus(InsuranceCompanyStatus status) {
        if (status == null) {
            throw new RuntimeException("Status is required");
        }

        List<InsuranceCompany> insuranceCompanies = insuranceCompanyRepository.findByStatus(status);
        return insuranceCompanies.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Insurance Companies by City
    public List<InsuranceCompanyResponseDTO> getInsuranceCompaniesByCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new RuntimeException("City is required");
        }

        List<InsuranceCompany> insuranceCompanies = insuranceCompanyRepository.findByCity(city);
        return insuranceCompanies.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Insurance Companies by State
    public List<InsuranceCompanyResponseDTO> getInsuranceCompaniesByState(String state) {
        if (state == null || state.trim().isEmpty()) {
            throw new RuntimeException("State is required");
        }

        List<InsuranceCompany> insuranceCompanies = insuranceCompanyRepository.findByState(state);
        return insuranceCompanies.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Insurance Companies by Country
    public List<InsuranceCompanyResponseDTO> getInsuranceCompaniesByCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new RuntimeException("Country is required");
        }

        List<InsuranceCompany> insuranceCompanies = insuranceCompanyRepository.findByCountry(country);
        return insuranceCompanies.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Insurance Companies by Network Hospital
    public List<InsuranceCompanyResponseDTO> getInsuranceCompaniesByNetworkHospital(String hospitalName) {
        if (hospitalName == null || hospitalName.trim().isEmpty()) {
            throw new RuntimeException("Hospital name is required");
        }

        List<InsuranceCompany> insuranceCompanies = insuranceCompanyRepository.findByNetworkHospital(hospitalName);
        return insuranceCompanies.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Update Insurance Company
    public InsuranceCompanyResponseDTO updateInsuranceCompany(Long id, InsuranceCompanyRequestDTO insuranceCompanyRequestDTO) {
        if (id == null) {
            throw new RuntimeException("Insurance company ID is required");
        }

        if (insuranceCompanyRequestDTO == null) {
            throw new RuntimeException("Insurance company data is required");
        }

        Optional<InsuranceCompany> existingInsuranceCompany = insuranceCompanyRepository.findById(id);
        if (existingInsuranceCompany.isEmpty()) {
            throw new RuntimeException("Insurance company not found");
        }

        InsuranceCompany insuranceCompany = existingInsuranceCompany.get();

        // Check if code is being changed and if it already exists
        if (!insuranceCompany.getCode().equals(insuranceCompanyRequestDTO.getCode()) && 
            insuranceCompanyRepository.existsByCode(insuranceCompanyRequestDTO.getCode())) {
            throw new RuntimeException("Insurance company code already exists");
        }

        // Check if email is being changed and if it already exists
        if (!insuranceCompany.getEmail().equals(insuranceCompanyRequestDTO.getEmail()) && 
            insuranceCompanyRepository.existsByEmail(insuranceCompanyRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check if phone is being changed and if it already exists
        if (!insuranceCompany.getPhone().equals(insuranceCompanyRequestDTO.getPhone()) && 
            insuranceCompanyRepository.findByPhone(insuranceCompanyRequestDTO.getPhone()).isPresent()) {
            throw new RuntimeException("Phone already exists");
        }

        // Check if license number is being changed and if it already exists
        if (!insuranceCompany.getLicenseNumber().equals(insuranceCompanyRequestDTO.getLicenseNumber()) && 
            insuranceCompanyRepository.existsByLicenseNumber(insuranceCompanyRequestDTO.getLicenseNumber())) {
            throw new RuntimeException("License number already exists");
        }

        // Check if tax ID is being changed and if it already exists
        if (!insuranceCompany.getTaxId().equals(insuranceCompanyRequestDTO.getTaxId()) && 
            insuranceCompanyRepository.existsByTaxId(insuranceCompanyRequestDTO.getTaxId())) {
            throw new RuntimeException("Tax ID already exists");
        }

        // Update insurance company fields
        insuranceCompany.setName(insuranceCompanyRequestDTO.getName());
        insuranceCompany.setCode(insuranceCompanyRequestDTO.getCode());
        insuranceCompany.setType(insuranceCompanyRequestDTO.getType());
        insuranceCompany.setAddress(insuranceCompanyRequestDTO.getAddress());
        insuranceCompany.setCity(insuranceCompanyRequestDTO.getCity());
        insuranceCompany.setState(insuranceCompanyRequestDTO.getState());
        insuranceCompany.setZipCode(insuranceCompanyRequestDTO.getZipCode());
        insuranceCompany.setCountry(insuranceCompanyRequestDTO.getCountry());
        insuranceCompany.setPhone(insuranceCompanyRequestDTO.getPhone());
        insuranceCompany.setEmail(insuranceCompanyRequestDTO.getEmail());
        insuranceCompany.setWebsite(insuranceCompanyRequestDTO.getWebsite());
        insuranceCompany.setContactPerson(insuranceCompanyRequestDTO.getContactPerson());
        insuranceCompany.setContactPhone(insuranceCompanyRequestDTO.getContactPhone());
        insuranceCompany.setContactEmail(insuranceCompanyRequestDTO.getContactEmail());
        insuranceCompany.setDescription(insuranceCompanyRequestDTO.getDescription());
        insuranceCompany.setLicenseNumber(insuranceCompanyRequestDTO.getLicenseNumber());
        insuranceCompany.setTaxId(insuranceCompanyRequestDTO.getTaxId());
        insuranceCompany.setCoverageDetails(insuranceCompanyRequestDTO.getCoverageDetails());
        insuranceCompany.setPolicyTypes(insuranceCompanyRequestDTO.getPolicyTypes());
        insuranceCompany.setNetworkHospitals(insuranceCompanyRequestDTO.getNetworkHospitals());
        insuranceCompany.setClaimProcess(insuranceCompanyRequestDTO.getClaimProcess());
        insuranceCompany.setTermsAndConditions(insuranceCompanyRequestDTO.getTermsAndConditions());
        if (insuranceCompanyRequestDTO.getStatus() != null) {
            insuranceCompany.setStatus(insuranceCompanyRequestDTO.getStatus());
        }

        InsuranceCompany updatedInsuranceCompany = insuranceCompanyRepository.save(insuranceCompany);
        return convertToResponseDTO(updatedInsuranceCompany);
    }

    // Update Insurance Company Status
    public InsuranceCompanyResponseDTO updateInsuranceCompanyStatus(Long id, InsuranceCompanyStatus status) {
        if (id == null) {
            throw new RuntimeException("Insurance company ID is required");
        }

        if (status == null) {
            throw new RuntimeException("Status is required");
        }

        Optional<InsuranceCompany> existingInsuranceCompany = insuranceCompanyRepository.findById(id);
        if (existingInsuranceCompany.isEmpty()) {
            throw new RuntimeException("Insurance company not found");
        }

        InsuranceCompany insuranceCompany = existingInsuranceCompany.get();
        insuranceCompany.setStatus(status);

        InsuranceCompany updatedInsuranceCompany = insuranceCompanyRepository.save(insuranceCompany);
        return convertToResponseDTO(updatedInsuranceCompany);
    }

    // Delete Insurance Company
    public void deleteInsuranceCompany(Long id) {
        if (id == null) {
            throw new RuntimeException("Insurance company ID is required");
        }

        Optional<InsuranceCompany> insuranceCompany = insuranceCompanyRepository.findById(id);
        if (insuranceCompany.isEmpty()) {
            throw new RuntimeException("Insurance company not found");
        }

        insuranceCompanyRepository.deleteById(id);
    }

    // Convert Entity to Response DTO
    private InsuranceCompanyResponseDTO convertToResponseDTO(InsuranceCompany insuranceCompany) {
        InsuranceCompanyResponseDTO response = new InsuranceCompanyResponseDTO();
        response.setId(insuranceCompany.getId());
        response.setName(insuranceCompany.getName());
        response.setCode(insuranceCompany.getCode());
        response.setType(insuranceCompany.getType());
        response.setAddress(insuranceCompany.getAddress());
        response.setCity(insuranceCompany.getCity());
        response.setState(insuranceCompany.getState());
        response.setZipCode(insuranceCompany.getZipCode());
        response.setCountry(insuranceCompany.getCountry());
        response.setPhone(insuranceCompany.getPhone());
        response.setEmail(insuranceCompany.getEmail());
        response.setWebsite(insuranceCompany.getWebsite());
        response.setContactPerson(insuranceCompany.getContactPerson());
        response.setContactPhone(insuranceCompany.getContactPhone());
        response.setContactEmail(insuranceCompany.getContactEmail());
        response.setDescription(insuranceCompany.getDescription());
        response.setLicenseNumber(insuranceCompany.getLicenseNumber());
        response.setTaxId(insuranceCompany.getTaxId());
        response.setCoverageDetails(insuranceCompany.getCoverageDetails());
        response.setPolicyTypes(insuranceCompany.getPolicyTypes());
        response.setNetworkHospitals(insuranceCompany.getNetworkHospitals());
        response.setClaimProcess(insuranceCompany.getClaimProcess());
        response.setTermsAndConditions(insuranceCompany.getTermsAndConditions());
        response.setStatus(insuranceCompany.getStatus());
        response.setCreatedAt(insuranceCompany.getCreatedAt());
        response.setUpdatedAt(insuranceCompany.getUpdatedAt());
        
        return response;
    }
} 