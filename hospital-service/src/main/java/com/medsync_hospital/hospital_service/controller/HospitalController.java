package com.medsync_hospital.hospital_service.controller;

import com.medsync_hospital.hospital_service.dto.HospitalRequestDTO;
import com.medsync_hospital.hospital_service.dto.HospitalResponseDTO;
import com.medsync_hospital.hospital_service.enums.HospitalStatus;
import com.medsync_hospital.hospital_service.enums.HospitalType;
import com.medsync_hospital.hospital_service.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    // Create Hospital
    @PostMapping
    public ResponseEntity<HospitalResponseDTO> createHospital(@RequestBody HospitalRequestDTO hospitalRequestDTO) {
        try {
            HospitalResponseDTO response = hospitalService.createHospital(hospitalRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get All Hospitals
    @GetMapping
    public ResponseEntity<List<HospitalResponseDTO>> getAllHospitals() {
        try {
            List<HospitalResponseDTO> hospitalList = hospitalService.getAllHospitals();
            return ResponseEntity.ok(hospitalList);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get Hospital by ID
    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> getHospitalById(@PathVariable Long id) {
        try {
            HospitalResponseDTO hospital = hospitalService.getHospitalById(id);
            return ResponseEntity.ok(hospital);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Hospital by Code
    @GetMapping("/code/{code}")
    public ResponseEntity<HospitalResponseDTO> getHospitalByCode(@PathVariable String code) {
        try {
            HospitalResponseDTO hospital = hospitalService.getHospitalByCode(code);
            return ResponseEntity.ok(hospital);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Hospital by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<HospitalResponseDTO> getHospitalByEmail(@PathVariable String email) {
        try {
            HospitalResponseDTO hospital = hospitalService.getHospitalByEmail(email);
            return ResponseEntity.ok(hospital);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Search Hospitals by Name
    @GetMapping("/search")
    public ResponseEntity<List<HospitalResponseDTO>> searchHospitalsByName(@RequestParam String name) {
        try {
            List<HospitalResponseDTO> hospitals = hospitalService.searchHospitalsByName(name);
            return ResponseEntity.ok(hospitals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Hospitals by Type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<HospitalResponseDTO>> getHospitalsByType(@PathVariable HospitalType type) {
        try {
            List<HospitalResponseDTO> hospitals = hospitalService.getHospitalsByType(type);
            return ResponseEntity.ok(hospitals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Hospitals by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<HospitalResponseDTO>> getHospitalsByStatus(@PathVariable HospitalStatus status) {
        try {
            List<HospitalResponseDTO> hospitals = hospitalService.getHospitalsByStatus(status);
            return ResponseEntity.ok(hospitals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Hospitals by City
    @GetMapping("/city/{city}")
    public ResponseEntity<List<HospitalResponseDTO>> getHospitalsByCity(@PathVariable String city) {
        try {
            List<HospitalResponseDTO> hospitals = hospitalService.getHospitalsByCity(city);
            return ResponseEntity.ok(hospitals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Hospitals by State
    @GetMapping("/state/{state}")
    public ResponseEntity<List<HospitalResponseDTO>> getHospitalsByState(@PathVariable String state) {
        try {
            List<HospitalResponseDTO> hospitals = hospitalService.getHospitalsByState(state);
            return ResponseEntity.ok(hospitals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Hospitals by Country
    @GetMapping("/country/{country}")
    public ResponseEntity<List<HospitalResponseDTO>> getHospitalsByCountry(@PathVariable String country) {
        try {
            List<HospitalResponseDTO> hospitals = hospitalService.getHospitalsByCountry(country);
            return ResponseEntity.ok(hospitals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Hospitals with Available Beds
    @GetMapping("/available-beds")
    public ResponseEntity<List<HospitalResponseDTO>> getHospitalsWithAvailableBeds() {
        try {
            List<HospitalResponseDTO> hospitals = hospitalService.getHospitalsWithAvailableBeds();
            return ResponseEntity.ok(hospitals);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get Hospitals with Minimum Beds
    @GetMapping("/minimum-beds")
    public ResponseEntity<List<HospitalResponseDTO>> getHospitalsWithMinimumBeds(@RequestParam Integer minBeds) {
        try {
            List<HospitalResponseDTO> hospitals = hospitalService.getHospitalsWithMinimumBeds(minBeds);
            return ResponseEntity.ok(hospitals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Hospitals with Minimum Doctors
    @GetMapping("/minimum-doctors")
    public ResponseEntity<List<HospitalResponseDTO>> getHospitalsWithMinimumDoctors(@RequestParam Integer minDoctors) {
        try {
            List<HospitalResponseDTO> hospitals = hospitalService.getHospitalsWithMinimumDoctors(minDoctors);
            return ResponseEntity.ok(hospitals);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update Hospital
    @PutMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> updateHospital(@PathVariable Long id, @RequestBody HospitalRequestDTO hospitalRequestDTO) {
        try {
            HospitalResponseDTO response = hospitalService.updateHospital(id, hospitalRequestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update Hospital Status
    @PatchMapping("/{id}/status")
    public ResponseEntity<HospitalResponseDTO> updateHospitalStatus(@PathVariable Long id, @RequestParam HospitalStatus status) {
        try {
            HospitalResponseDTO response = hospitalService.updateHospitalStatus(id, status);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update Hospital Bed Availability
    @PatchMapping("/{id}/beds")
    public ResponseEntity<HospitalResponseDTO> updateHospitalBedAvailability(@PathVariable Long id, @RequestParam Integer availableBeds) {
        try {
            HospitalResponseDTO response = hospitalService.updateHospitalBedAvailability(id, availableBeds);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete Hospital
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
        try {
            hospitalService.deleteHospital(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 