package com.medsync_hospital.patient_service.controller;

import com.medsync_hospital.patient_service.dto.PatientRequestDTO;
import com.medsync_hospital.patient_service.dto.PatientResponseDTO;
import com.medsync_hospital.patient_service.enums.BloodGroup;
import com.medsync_hospital.patient_service.enums.Gender;
import com.medsync_hospital.patient_service.enums.PatientStatus;
import com.medsync_hospital.patient_service.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Create Patient
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        try {
            PatientResponseDTO response = patientService.createPatient(patientRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get All Patients
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        try {
            List<PatientResponseDTO> patientList = patientService.getAllPatients();
            return ResponseEntity.ok(patientList);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get Patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        try {
            PatientResponseDTO patient = patientService.getPatientById(id);
            return ResponseEntity.ok(patient);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Patient by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<PatientResponseDTO> getPatientByEmail(@PathVariable String email) {
        try {
            PatientResponseDTO patient = patientService.getPatientByEmail(email);
            return ResponseEntity.ok(patient);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Patient by Phone
    @GetMapping("/phone/{phone}")
    public ResponseEntity<PatientResponseDTO> getPatientByPhone(@PathVariable String phone) {
        try {
            PatientResponseDTO patient = patientService.getPatientByPhone(phone);
            return ResponseEntity.ok(patient);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Search Patients by Name
    @GetMapping("/search")
    public ResponseEntity<List<PatientResponseDTO>> searchPatientsByName(@RequestParam String name) {
        try {
            List<PatientResponseDTO> patients = patientService.searchPatientsByName(name);
            return ResponseEntity.ok(patients);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Patients by Gender
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<PatientResponseDTO>> getPatientsByGender(@PathVariable Gender gender) {
        try {
            List<PatientResponseDTO> patients = patientService.getPatientsByGender(gender);
            return ResponseEntity.ok(patients);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Patients by Blood Group
    @GetMapping("/blood-group/{bloodGroup}")
    public ResponseEntity<List<PatientResponseDTO>> getPatientsByBloodGroup(@PathVariable BloodGroup bloodGroup) {
        try {
            List<PatientResponseDTO> patients = patientService.getPatientsByBloodGroup(bloodGroup);
            return ResponseEntity.ok(patients);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Patients by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PatientResponseDTO>> getPatientsByStatus(@PathVariable PatientStatus status) {
        try {
            List<PatientResponseDTO> patients = patientService.getPatientsByStatus(status);
            return ResponseEntity.ok(patients);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Patients by City
    @GetMapping("/city/{city}")
    public ResponseEntity<List<PatientResponseDTO>> getPatientsByCity(@PathVariable String city) {
        try {
            List<PatientResponseDTO> patients = patientService.getPatientsByCity(city);
            return ResponseEntity.ok(patients);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Patients by State
    @GetMapping("/state/{state}")
    public ResponseEntity<List<PatientResponseDTO>> getPatientsByState(@PathVariable String state) {
        try {
            List<PatientResponseDTO> patients = patientService.getPatientsByState(state);
            return ResponseEntity.ok(patients);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Patients by Date of Birth Range
    @GetMapping("/date-range")
    public ResponseEntity<List<PatientResponseDTO>> getPatientsByDateOfBirthRange(
            @RequestParam LocalDate startDate, 
            @RequestParam LocalDate endDate) {
        try {
            List<PatientResponseDTO> patients = patientService.getPatientsByDateOfBirthRange(startDate, endDate);
            return ResponseEntity.ok(patients);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update Patient
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id, @RequestBody PatientRequestDTO patientRequestDTO) {
        try {
            PatientResponseDTO response = patientService.updatePatient(id, patientRequestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update Patient Status
    @PatchMapping("/{id}/status")
    public ResponseEntity<PatientResponseDTO> updatePatientStatus(@PathVariable Long id, @RequestParam PatientStatus status) {
        try {
            PatientResponseDTO response = patientService.updatePatientStatus(id, status);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete Patient
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 