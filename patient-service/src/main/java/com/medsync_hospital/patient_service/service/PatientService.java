package com.medsync_hospital.patient_service.service;

import com.medsync_hospital.patient_service.dto.PatientRequestDTO;
import com.medsync_hospital.patient_service.dto.PatientResponseDTO;
import com.medsync_hospital.patient_service.entity.Patient;
import com.medsync_hospital.patient_service.enums.BloodGroup;
import com.medsync_hospital.patient_service.enums.Gender;
import com.medsync_hospital.patient_service.enums.PatientStatus;
import com.medsync_hospital.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // Create Patient
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        // Basic validation
        if (patientRequestDTO == null) {
            throw new RuntimeException("Patient data is required");
        }

        // Check if email already exists
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check if phone already exists
        Optional<Patient> existingPhone = patientRepository.findByPhone(patientRequestDTO.getPhone());
        if (existingPhone.isPresent()) {
            throw new RuntimeException("Phone already exists");
        }

        // Create new patient
        Patient patient = new Patient();
        patient.setFirstName(patientRequestDTO.getFirstName());
        patient.setLastName(patientRequestDTO.getLastName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setPhone(patientRequestDTO.getPhone());
        patient.setDateOfBirth(patientRequestDTO.getDateOfBirth());
        patient.setGender(patientRequestDTO.getGender());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setCity(patientRequestDTO.getCity());
        patient.setState(patientRequestDTO.getState());
        patient.setZipCode(patientRequestDTO.getZipCode());
        patient.setEmergencyContactName(patientRequestDTO.getEmergencyContactName());
        patient.setEmergencyContactPhone(patientRequestDTO.getEmergencyContactPhone());
        patient.setBloodGroup(patientRequestDTO.getBloodGroup());
        patient.setMedicalHistory(patientRequestDTO.getMedicalHistory());
        patient.setAllergies(patientRequestDTO.getAllergies());
        patient.setCurrentMedications(patientRequestDTO.getCurrentMedications());
        patient.setStatus(patientRequestDTO.getStatus() != null ? patientRequestDTO.getStatus() : PatientStatus.ACTIVE);

        Patient savedPatient = patientRepository.save(patient);
        return convertToResponseDTO(savedPatient);
    }

    // Get All Patients
    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patientList = patientRepository.findAll();
        return patientList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Patient by ID
    public PatientResponseDTO getPatientById(Long id) {
        if (id == null) {
            throw new RuntimeException("Patient ID is required");
        }

        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new RuntimeException("Patient not found");
        }

        return convertToResponseDTO(patient.get());
    }

    // Get Patient by Email
    public PatientResponseDTO getPatientByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isEmpty()) {
            throw new RuntimeException("Patient not found");
        }

        return convertToResponseDTO(patient.get());
    }

    // Get Patient by Phone
    public PatientResponseDTO getPatientByPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new RuntimeException("Phone is required");
        }

        Optional<Patient> patient = patientRepository.findByPhone(phone);
        if (patient.isEmpty()) {
            throw new RuntimeException("Patient not found");
        }

        return convertToResponseDTO(patient.get());
    }

    // Search Patients by Name
    public List<PatientResponseDTO> searchPatientsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("Name is required");
        }

        List<Patient> patients = patientRepository.findByNameContaining(name);
        return patients.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Patients by Gender
    public List<PatientResponseDTO> getPatientsByGender(Gender gender) {
        if (gender == null) {
            throw new RuntimeException("Gender is required");
        }

        List<Patient> patients = patientRepository.findByGender(gender);
        return patients.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Patients by Blood Group
    public List<PatientResponseDTO> getPatientsByBloodGroup(BloodGroup bloodGroup) {
        if (bloodGroup == null) {
            throw new RuntimeException("Blood group is required");
        }

        List<Patient> patients = patientRepository.findByBloodGroup(bloodGroup);
        return patients.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Patients by Status
    public List<PatientResponseDTO> getPatientsByStatus(PatientStatus status) {
        if (status == null) {
            throw new RuntimeException("Status is required");
        }

        List<Patient> patients = patientRepository.findByStatus(status);
        return patients.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Patients by City
    public List<PatientResponseDTO> getPatientsByCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new RuntimeException("City is required");
        }

        List<Patient> patients = patientRepository.findByCity(city);
        return patients.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Patients by State
    public List<PatientResponseDTO> getPatientsByState(String state) {
        if (state == null || state.trim().isEmpty()) {
            throw new RuntimeException("State is required");
        }

        List<Patient> patients = patientRepository.findByState(state);
        return patients.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Patients by Date of Birth Range
    public List<PatientResponseDTO> getPatientsByDateOfBirthRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start date and end date are required");
        }

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date must be before end date");
        }

        List<Patient> patients = patientRepository.findByDateOfBirthBetween(startDate, endDate);
        return patients.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Update Patient
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO) {
        if (id == null) {
            throw new RuntimeException("Patient ID is required");
        }

        if (patientRequestDTO == null) {
            throw new RuntimeException("Patient data is required");
        }

        Optional<Patient> existingPatient = patientRepository.findById(id);
        if (existingPatient.isEmpty()) {
            throw new RuntimeException("Patient not found");
        }

        Patient patient = existingPatient.get();

        // Check if email is being changed and if it already exists
        if (!patient.getEmail().equals(patientRequestDTO.getEmail()) && 
            patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check if phone is being changed and if it already exists
        if (!patient.getPhone().equals(patientRequestDTO.getPhone()) && 
            patientRepository.findByPhone(patientRequestDTO.getPhone()).isPresent()) {
            throw new RuntimeException("Phone already exists");
        }

        // Update patient fields
        patient.setFirstName(patientRequestDTO.getFirstName());
        patient.setLastName(patientRequestDTO.getLastName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setPhone(patientRequestDTO.getPhone());
        patient.setDateOfBirth(patientRequestDTO.getDateOfBirth());
        patient.setGender(patientRequestDTO.getGender());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setCity(patientRequestDTO.getCity());
        patient.setState(patientRequestDTO.getState());
        patient.setZipCode(patientRequestDTO.getZipCode());
        patient.setEmergencyContactName(patientRequestDTO.getEmergencyContactName());
        patient.setEmergencyContactPhone(patientRequestDTO.getEmergencyContactPhone());
        patient.setBloodGroup(patientRequestDTO.getBloodGroup());
        patient.setMedicalHistory(patientRequestDTO.getMedicalHistory());
        patient.setAllergies(patientRequestDTO.getAllergies());
        patient.setCurrentMedications(patientRequestDTO.getCurrentMedications());
        if (patientRequestDTO.getStatus() != null) {
            patient.setStatus(patientRequestDTO.getStatus());
        }

        Patient updatedPatient = patientRepository.save(patient);
        return convertToResponseDTO(updatedPatient);
    }

    // Update Patient Status
    public PatientResponseDTO updatePatientStatus(Long id, PatientStatus status) {
        if (id == null) {
            throw new RuntimeException("Patient ID is required");
        }

        if (status == null) {
            throw new RuntimeException("Status is required");
        }

        Optional<Patient> existingPatient = patientRepository.findById(id);
        if (existingPatient.isEmpty()) {
            throw new RuntimeException("Patient not found");
        }

        Patient patient = existingPatient.get();
        patient.setStatus(status);

        Patient updatedPatient = patientRepository.save(patient);
        return convertToResponseDTO(updatedPatient);
    }

    // Delete Patient
    public void deletePatient(Long id) {
        if (id == null) {
            throw new RuntimeException("Patient ID is required");
        }

        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new RuntimeException("Patient not found");
        }

        patientRepository.deleteById(id);
    }

    // Convert Entity to Response DTO
    private PatientResponseDTO convertToResponseDTO(Patient patient) {
        PatientResponseDTO response = new PatientResponseDTO();
        response.setId(patient.getId());
        response.setFirstName(patient.getFirstName());
        response.setLastName(patient.getLastName());
        response.setEmail(patient.getEmail());
        response.setPhone(patient.getPhone());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setGender(patient.getGender());
        response.setAddress(patient.getAddress());
        response.setCity(patient.getCity());
        response.setState(patient.getState());
        response.setZipCode(patient.getZipCode());
        response.setEmergencyContactName(patient.getEmergencyContactName());
        response.setEmergencyContactPhone(patient.getEmergencyContactPhone());
        response.setBloodGroup(patient.getBloodGroup());
        response.setMedicalHistory(patient.getMedicalHistory());
        response.setAllergies(patient.getAllergies());
        response.setCurrentMedications(patient.getCurrentMedications());
        response.setStatus(patient.getStatus());
        response.setCreatedAt(patient.getCreatedAt());
        response.setUpdatedAt(patient.getUpdatedAt());
        
        return response;
    }
} 