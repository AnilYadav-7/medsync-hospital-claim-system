package com.medsync_hospital.patient_service.service;

import com.medsync_hospital.patient_service.model.Patient;
import com.medsync_hospital.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;
    
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
    
    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
    
    public Optional<Patient> getPatientByInsuranceNumber(String insuranceNumber) {
        return patientRepository.findByInsuranceNumber(insuranceNumber);
    }
    
    public List<Patient> searchPatients(String searchTerm) {
        return patientRepository.searchPatients(searchTerm);
    }
    
    public Patient createPatient(Patient patient) {
        // Check if email already exists
        if (patient.getEmail() != null && patientRepository.existsByEmail(patient.getEmail())) {
            throw new IllegalArgumentException("Patient with email " + patient.getEmail() + " already exists");
        }
        
        // Check if insurance number already exists
        if (patient.getInsuranceNumber() != null && patientRepository.existsByInsuranceNumber(patient.getInsuranceNumber())) {
            throw new IllegalArgumentException("Patient with insurance number " + patient.getInsuranceNumber() + " already exists");
        }
        
        return patientRepository.save(patient);
    }
    
    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        
        // Check if email is being changed and if new email already exists
        if (patientDetails.getEmail() != null && 
            !patientDetails.getEmail().equals(patient.getEmail()) &&
            patientRepository.existsByEmail(patientDetails.getEmail())) {
            throw new IllegalArgumentException("Patient with email " + patientDetails.getEmail() + " already exists");
        }
        
        // Check if insurance number is being changed and if new insurance number already exists
        if (patientDetails.getInsuranceNumber() != null && 
            !patientDetails.getInsuranceNumber().equals(patient.getInsuranceNumber()) &&
            patientRepository.existsByInsuranceNumber(patientDetails.getInsuranceNumber())) {
            throw new IllegalArgumentException("Patient with insurance number " + patientDetails.getInsuranceNumber() + " already exists");
        }
        
        // Update fields
        if (patientDetails.getFirstName() != null) {
            patient.setFirstName(patientDetails.getFirstName());
        }
        if (patientDetails.getLastName() != null) {
            patient.setLastName(patientDetails.getLastName());
        }
        if (patientDetails.getEmail() != null) {
            patient.setEmail(patientDetails.getEmail());
        }
        if (patientDetails.getPhoneNumber() != null) {
            patient.setPhoneNumber(patientDetails.getPhoneNumber());
        }
        if (patientDetails.getDateOfBirth() != null) {
            patient.setDateOfBirth(patientDetails.getDateOfBirth());
        }
        if (patientDetails.getGender() != null) {
            patient.setGender(patientDetails.getGender());
        }
        if (patientDetails.getAddress() != null) {
            patient.setAddress(patientDetails.getAddress());
        }
        if (patientDetails.getInsuranceNumber() != null) {
            patient.setInsuranceNumber(patientDetails.getInsuranceNumber());
        }
        if (patientDetails.getEmergencyContactName() != null) {
            patient.setEmergencyContactName(patientDetails.getEmergencyContactName());
        }
        if (patientDetails.getEmergencyContactPhone() != null) {
            patient.setEmergencyContactPhone(patientDetails.getEmergencyContactPhone());
        }
        
        return patientRepository.save(patient);
    }
    
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        patientRepository.delete(patient);
    }
    
    public boolean existsById(Long id) {
        return patientRepository.existsById(id);
    }
}