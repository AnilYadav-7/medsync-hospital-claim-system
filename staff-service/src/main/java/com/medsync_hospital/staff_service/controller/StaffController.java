package com.medsync_hospital.staff_service.controller;

import com.medsync_hospital.staff_service.dto.LoginRequestDTO;
import com.medsync_hospital.staff_service.dto.LoginResponseDTO;
import com.medsync_hospital.staff_service.dto.StaffRequestDTO;
import com.medsync_hospital.staff_service.dto.StaffResponseDTO;
import com.medsync_hospital.staff_service.enums.StaffRole;
import com.medsync_hospital.staff_service.enums.StaffStatus;
import com.medsync_hospital.staff_service.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private StaffService staffService;

    // Staff Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginStaff(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            LoginResponseDTO response = staffService.loginStaff(loginRequestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Create Staff
    @PostMapping
    public ResponseEntity<StaffResponseDTO> createStaff(@RequestBody StaffRequestDTO staffRequestDTO) {
        logger.info("Creating new staff member: {}", staffRequestDTO.getEmail());
        try {
            StaffResponseDTO response = staffService.createStaff(staffRequestDTO);
            logger.info("Successfully created staff member with ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            logger.error("Failed to create staff member: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Get All Staff
    @GetMapping
    public ResponseEntity<List<StaffResponseDTO>> getAllStaff() {
        logger.info("Retrieving all staff members");
        try {
            List<StaffResponseDTO> staffList = staffService.getAllStaff();
            logger.info("Successfully retrieved {} staff members", staffList.size());
            return ResponseEntity.ok(staffList);
        } catch (RuntimeException e) {
            logger.error("Failed to retrieve staff members: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get Staff by ID
    @GetMapping("/{id}")
    public ResponseEntity<StaffResponseDTO> getStaffById(@PathVariable Long id) {
        try {
            StaffResponseDTO staff = staffService.getStaffById(id);
            return ResponseEntity.ok(staff);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get Staff by Role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<StaffResponseDTO>> getStaffByRole(@PathVariable StaffRole role) {
        try {
            List<StaffResponseDTO> staffList = staffService.getStaffByRole(role);
            return ResponseEntity.ok(staffList);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get Staff by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<StaffResponseDTO>> getStaffByStatus(@PathVariable StaffStatus status) {
        try {
            List<StaffResponseDTO> staffList = staffService.getStaffByStatus(status);
            return ResponseEntity.ok(staffList);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update Staff
    @PutMapping("/{id}")
    public ResponseEntity<StaffResponseDTO> updateStaff(@PathVariable Long id, @RequestBody StaffRequestDTO staffRequestDTO) {
        try {
            StaffResponseDTO response = staffService.updateStaff(id, staffRequestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete Staff
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        try {
            staffService.deleteStaff(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update Staff Status
    @PatchMapping("/{id}/status")
    public ResponseEntity<StaffResponseDTO> updateStaffStatus(@PathVariable Long id, @RequestParam StaffStatus status) {
        try {
            StaffResponseDTO response = staffService.updateStaffStatus(id, status);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 