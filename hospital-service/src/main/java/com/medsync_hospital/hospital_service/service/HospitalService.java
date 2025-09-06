package com.medsync_hospital.hospital_service.service;

import com.medsync_hospital.hospital_service.dto.HospitalRequestDTO;
import com.medsync_hospital.hospital_service.dto.HospitalResponseDTO;
import com.medsync_hospital.hospital_service.entity.Hospital;
import com.medsync_hospital.hospital_service.enums.HospitalStatus;
import com.medsync_hospital.hospital_service.enums.HospitalType;
import com.medsync_hospital.hospital_service.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    // Create Hospital
    public HospitalResponseDTO createHospital(HospitalRequestDTO hospitalRequestDTO) {
        // Basic validation
        if (hospitalRequestDTO == null) {
            throw new RuntimeException("Hospital data is required");
        }

        // Check if code already exists
        if (hospitalRepository.existsByCode(hospitalRequestDTO.getCode())) {
            throw new RuntimeException("Hospital code already exists");
        }

        // Check if email already exists
        if (hospitalRepository.existsByEmail(hospitalRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check if phone already exists
        Optional<Hospital> existingPhone = hospitalRepository.findByPhone(hospitalRequestDTO.getPhone());
        if (existingPhone.isPresent()) {
            throw new RuntimeException("Phone already exists");
        }

        // Create new hospital
        Hospital hospital = new Hospital();
        hospital.setName(hospitalRequestDTO.getName());
        hospital.setCode(hospitalRequestDTO.getCode());
        hospital.setType(hospitalRequestDTO.getType());
        hospital.setAddress(hospitalRequestDTO.getAddress());
        hospital.setCity(hospitalRequestDTO.getCity());
        hospital.setState(hospitalRequestDTO.getState());
        hospital.setZipCode(hospitalRequestDTO.getZipCode());
        hospital.setCountry(hospitalRequestDTO.getCountry());
        hospital.setPhone(hospitalRequestDTO.getPhone());
        hospital.setEmail(hospitalRequestDTO.getEmail());
        hospital.setWebsite(hospitalRequestDTO.getWebsite());
        hospital.setContactPerson(hospitalRequestDTO.getContactPerson());
        hospital.setContactPhone(hospitalRequestDTO.getContactPhone());
        hospital.setContactEmail(hospitalRequestDTO.getContactEmail());
        hospital.setDescription(hospitalRequestDTO.getDescription());
        hospital.setTotalBeds(hospitalRequestDTO.getTotalBeds());
        hospital.setAvailableBeds(hospitalRequestDTO.getAvailableBeds());
        hospital.setTotalDoctors(hospitalRequestDTO.getTotalDoctors());
        hospital.setTotalNurses(hospitalRequestDTO.getTotalNurses());
        hospital.setFacilities(hospitalRequestDTO.getFacilities());
        hospital.setSpecialties(hospitalRequestDTO.getSpecialties());
        hospital.setDepartments(hospitalRequestDTO.getDepartments());
        hospital.setStatus(hospitalRequestDTO.getStatus() != null ? hospitalRequestDTO.getStatus() : HospitalStatus.ACTIVE);

        Hospital savedHospital = hospitalRepository.save(hospital);
        return convertToResponseDTO(savedHospital);
    }

    // Get All Hospitals
    public List<HospitalResponseDTO> getAllHospitals() {
        List<Hospital> hospitalList = hospitalRepository.findAll();
        return hospitalList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Hospital by ID
    public HospitalResponseDTO getHospitalById(Long id) {
        if (id == null) {
            throw new RuntimeException("Hospital ID is required");
        }

        Optional<Hospital> hospital = hospitalRepository.findById(id);
        if (hospital.isEmpty()) {
            throw new RuntimeException("Hospital not found");
        }

        return convertToResponseDTO(hospital.get());
    }

    // Get Hospital by Code
    public HospitalResponseDTO getHospitalByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new RuntimeException("Hospital code is required");
        }

        Optional<Hospital> hospital = hospitalRepository.findByCode(code);
        if (hospital.isEmpty()) {
            throw new RuntimeException("Hospital not found");
        }

        return convertToResponseDTO(hospital.get());
    }

    // Get Hospital by Email
    public HospitalResponseDTO getHospitalByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        Optional<Hospital> hospital = hospitalRepository.findByEmail(email);
        if (hospital.isEmpty()) {
            throw new RuntimeException("Hospital not found");
        }

        return convertToResponseDTO(hospital.get());
    }

    // Search Hospitals by Name
    public List<HospitalResponseDTO> searchHospitalsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("Name is required");
        }

        List<Hospital> hospitals = hospitalRepository.findByNameContainingIgnoreCase(name);
        return hospitals.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Hospitals by Type
    public List<HospitalResponseDTO> getHospitalsByType(HospitalType type) {
        if (type == null) {
            throw new RuntimeException("Hospital type is required");
        }

        List<Hospital> hospitals = hospitalRepository.findByType(type);
        return hospitals.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Hospitals by Status
    public List<HospitalResponseDTO> getHospitalsByStatus(HospitalStatus status) {
        if (status == null) {
            throw new RuntimeException("Status is required");
        }

        List<Hospital> hospitals = hospitalRepository.findByStatus(status);
        return hospitals.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Hospitals by City
    public List<HospitalResponseDTO> getHospitalsByCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new RuntimeException("City is required");
        }

        List<Hospital> hospitals = hospitalRepository.findByCity(city);
        return hospitals.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Hospitals by State
    public List<HospitalResponseDTO> getHospitalsByState(String state) {
        if (state == null || state.trim().isEmpty()) {
            throw new RuntimeException("State is required");
        }

        List<Hospital> hospitals = hospitalRepository.findByState(state);
        return hospitals.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Hospitals by Country
    public List<HospitalResponseDTO> getHospitalsByCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new RuntimeException("Country is required");
        }

        List<Hospital> hospitals = hospitalRepository.findByCountry(country);
        return hospitals.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Hospitals with Available Beds
    public List<HospitalResponseDTO> getHospitalsWithAvailableBeds() {
        List<Hospital> hospitals = hospitalRepository.findHospitalsWithAvailableBeds();
        return hospitals.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Hospitals with Minimum Beds
    public List<HospitalResponseDTO> getHospitalsWithMinimumBeds(Integer minBeds) {
        if (minBeds == null || minBeds < 0) {
            throw new RuntimeException("Minimum beds must be a positive number");
        }

        List<Hospital> hospitals = hospitalRepository.findHospitalsWithMinimumBeds(minBeds);
        return hospitals.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Hospitals with Minimum Doctors
    public List<HospitalResponseDTO> getHospitalsWithMinimumDoctors(Integer minDoctors) {
        if (minDoctors == null || minDoctors < 0) {
            throw new RuntimeException("Minimum doctors must be a positive number");
        }

        List<Hospital> hospitals = hospitalRepository.findHospitalsWithMinimumDoctors(minDoctors);
        return hospitals.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Update Hospital
    public HospitalResponseDTO updateHospital(Long id, HospitalRequestDTO hospitalRequestDTO) {
        if (id == null) {
            throw new RuntimeException("Hospital ID is required");
        }

        if (hospitalRequestDTO == null) {
            throw new RuntimeException("Hospital data is required");
        }

        Optional<Hospital> existingHospital = hospitalRepository.findById(id);
        if (existingHospital.isEmpty()) {
            throw new RuntimeException("Hospital not found");
        }

        Hospital hospital = existingHospital.get();

        // Check if code is being changed and if it already exists
        if (!hospital.getCode().equals(hospitalRequestDTO.getCode()) && 
            hospitalRepository.existsByCode(hospitalRequestDTO.getCode())) {
            throw new RuntimeException("Hospital code already exists");
        }

        // Check if email is being changed and if it already exists
        if (!hospital.getEmail().equals(hospitalRequestDTO.getEmail()) && 
            hospitalRepository.existsByEmail(hospitalRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check if phone is being changed and if it already exists
        if (!hospital.getPhone().equals(hospitalRequestDTO.getPhone()) && 
            hospitalRepository.findByPhone(hospitalRequestDTO.getPhone()).isPresent()) {
            throw new RuntimeException("Phone already exists");
        }

        // Update hospital fields
        hospital.setName(hospitalRequestDTO.getName());
        hospital.setCode(hospitalRequestDTO.getCode());
        hospital.setType(hospitalRequestDTO.getType());
        hospital.setAddress(hospitalRequestDTO.getAddress());
        hospital.setCity(hospitalRequestDTO.getCity());
        hospital.setState(hospitalRequestDTO.getState());
        hospital.setZipCode(hospitalRequestDTO.getZipCode());
        hospital.setCountry(hospitalRequestDTO.getCountry());
        hospital.setPhone(hospitalRequestDTO.getPhone());
        hospital.setEmail(hospitalRequestDTO.getEmail());
        hospital.setWebsite(hospitalRequestDTO.getWebsite());
        hospital.setContactPerson(hospitalRequestDTO.getContactPerson());
        hospital.setContactPhone(hospitalRequestDTO.getContactPhone());
        hospital.setContactEmail(hospitalRequestDTO.getContactEmail());
        hospital.setDescription(hospitalRequestDTO.getDescription());
        hospital.setTotalBeds(hospitalRequestDTO.getTotalBeds());
        hospital.setAvailableBeds(hospitalRequestDTO.getAvailableBeds());
        hospital.setTotalDoctors(hospitalRequestDTO.getTotalDoctors());
        hospital.setTotalNurses(hospitalRequestDTO.getTotalNurses());
        hospital.setFacilities(hospitalRequestDTO.getFacilities());
        hospital.setSpecialties(hospitalRequestDTO.getSpecialties());
        hospital.setDepartments(hospitalRequestDTO.getDepartments());
        if (hospitalRequestDTO.getStatus() != null) {
            hospital.setStatus(hospitalRequestDTO.getStatus());
        }

        Hospital updatedHospital = hospitalRepository.save(hospital);
        return convertToResponseDTO(updatedHospital);
    }

    // Update Hospital Status
    public HospitalResponseDTO updateHospitalStatus(Long id, HospitalStatus status) {
        if (id == null) {
            throw new RuntimeException("Hospital ID is required");
        }

        if (status == null) {
            throw new RuntimeException("Status is required");
        }

        Optional<Hospital> existingHospital = hospitalRepository.findById(id);
        if (existingHospital.isEmpty()) {
            throw new RuntimeException("Hospital not found");
        }

        Hospital hospital = existingHospital.get();
        hospital.setStatus(status);

        Hospital updatedHospital = hospitalRepository.save(hospital);
        return convertToResponseDTO(updatedHospital);
    }

    // Update Hospital Bed Availability
    public HospitalResponseDTO updateHospitalBedAvailability(Long id, Integer availableBeds) {
        if (id == null) {
            throw new RuntimeException("Hospital ID is required");
        }

        if (availableBeds == null || availableBeds < 0) {
            throw new RuntimeException("Available beds must be a positive number");
        }

        Optional<Hospital> existingHospital = hospitalRepository.findById(id);
        if (existingHospital.isEmpty()) {
            throw new RuntimeException("Hospital not found");
        }

        Hospital hospital = existingHospital.get();
        if (availableBeds > hospital.getTotalBeds()) {
            throw new RuntimeException("Available beds cannot exceed total beds");
        }

        hospital.setAvailableBeds(availableBeds);

        Hospital updatedHospital = hospitalRepository.save(hospital);
        return convertToResponseDTO(updatedHospital);
    }

    // Delete Hospital
    public void deleteHospital(Long id) {
        if (id == null) {
            throw new RuntimeException("Hospital ID is required");
        }

        Optional<Hospital> hospital = hospitalRepository.findById(id);
        if (hospital.isEmpty()) {
            throw new RuntimeException("Hospital not found");
        }

        hospitalRepository.deleteById(id);
    }

    // Convert Entity to Response DTO
    private HospitalResponseDTO convertToResponseDTO(Hospital hospital) {
        HospitalResponseDTO response = new HospitalResponseDTO();
        response.setId(hospital.getId());
        response.setName(hospital.getName());
        response.setCode(hospital.getCode());
        response.setType(hospital.getType());
        response.setAddress(hospital.getAddress());
        response.setCity(hospital.getCity());
        response.setState(hospital.getState());
        response.setZipCode(hospital.getZipCode());
        response.setCountry(hospital.getCountry());
        response.setPhone(hospital.getPhone());
        response.setEmail(hospital.getEmail());
        response.setWebsite(hospital.getWebsite());
        response.setContactPerson(hospital.getContactPerson());
        response.setContactPhone(hospital.getContactPhone());
        response.setContactEmail(hospital.getContactEmail());
        response.setDescription(hospital.getDescription());
        response.setTotalBeds(hospital.getTotalBeds());
        response.setAvailableBeds(hospital.getAvailableBeds());
        response.setTotalDoctors(hospital.getTotalDoctors());
        response.setTotalNurses(hospital.getTotalNurses());
        response.setFacilities(hospital.getFacilities());
        response.setSpecialties(hospital.getSpecialties());
        response.setDepartments(hospital.getDepartments());
        response.setStatus(hospital.getStatus());
        response.setCreatedAt(hospital.getCreatedAt());
        response.setUpdatedAt(hospital.getUpdatedAt());
        
        return response;
    }
} 