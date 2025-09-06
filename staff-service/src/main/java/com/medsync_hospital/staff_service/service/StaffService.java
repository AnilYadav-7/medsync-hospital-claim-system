package com.medsync_hospital.staff_service.service;

import com.medsync_hospital.staff_service.dto.LoginRequestDTO;
import com.medsync_hospital.staff_service.dto.LoginResponseDTO;
import com.medsync_hospital.staff_service.dto.StaffRequestDTO;
import com.medsync_hospital.staff_service.dto.StaffResponseDTO;
import com.medsync_hospital.staff_service.entity.Staff;
import com.medsync_hospital.staff_service.enums.StaffRole;
import com.medsync_hospital.staff_service.enums.StaffStatus;
import com.medsync_hospital.staff_service.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Staff Login
    public LoginResponseDTO loginStaff(LoginRequestDTO loginRequestDTO) {
        // Basic validation
        if (loginRequestDTO == null || loginRequestDTO.getEmailOrPhone() == null || 
            loginRequestDTO.getPassword() == null) {
            throw new RuntimeException("Email/Phone and password are required");
        }

        String emailOrPhone = loginRequestDTO.getEmailOrPhone().trim();
        String password = loginRequestDTO.getPassword();

        // Find staff by email or phone
        Staff staff = findStaffByEmailOrPhone(emailOrPhone);
        if (staff == null) {
            throw new RuntimeException("Staff not found");
        }

        // Check if staff is active
        if (staff.getStatus() != StaffStatus.ACTIVE) {
            throw new RuntimeException("Staff account is inactive");
        }

        // Check password
        if (!passwordEncoder.matches(password, staff.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Create response
        LoginResponseDTO response = new LoginResponseDTO();
        response.setId(staff.getId());
        response.setName(staff.getName());
        response.setRole(staff.getRole());
        response.setStatus(staff.getStatus());
        response.setToken("TOKEN_" + UUID.randomUUID().toString().substring(0, 8));
        
        return response;
    }

    // Create Staff
    public StaffResponseDTO createStaff(StaffRequestDTO staffRequestDTO) {
        // Basic validation
        if (staffRequestDTO == null) {
            throw new RuntimeException("Staff data is required");
        }

        // Check if email already exists
        if (staffRepository.existsByEmail(staffRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check if phone already exists
        Optional<Staff> existingPhone = staffRepository.findByPhone(staffRequestDTO.getPhone());
        if (existingPhone.isPresent()) {
            throw new RuntimeException("Phone already exists");
        }

        // Create new staff
        Staff staff = new Staff();
        staff.setName(staffRequestDTO.getName());
        staff.setEmail(staffRequestDTO.getEmail());
        staff.setPhone(staffRequestDTO.getPhone());
        staff.setRole(staffRequestDTO.getRole());
        staff.setStatus(staffRequestDTO.getStatus());
        staff.setPassword(passwordEncoder.encode(staffRequestDTO.getPassword()));

        Staff savedStaff = staffRepository.save(staff);
        return convertToResponseDTO(savedStaff);
    }

    // Get All Staff
    public List<StaffResponseDTO> getAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        return staffList.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // Get Staff by ID
    public StaffResponseDTO getStaffById(Long id) {
        if (id == null) {
            throw new RuntimeException("Staff ID is required");
        }

        Optional<Staff> staff = staffRepository.findById(id);
        if (staff.isEmpty()) {
            throw new RuntimeException("Staff not found");
        }

        return convertToResponseDTO(staff.get());
    }

    // Get Staff by Role
    public List<StaffResponseDTO> getStaffByRole(StaffRole role) {
        if (role == null) {
            throw new RuntimeException("Role is required");
        }

        List<Staff> staffList = staffRepository.findAllByRole(role);
        return staffList.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // Get Staff by Status
    public List<StaffResponseDTO> getStaffByStatus(StaffStatus status) {
        if (status == null) {
            throw new RuntimeException("Status is required");
        }

        List<Staff> staffList = staffRepository.findAllByStatus(status);
        return staffList.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // Update Staff
    public StaffResponseDTO updateStaff(Long id, StaffRequestDTO staffRequestDTO) {
        if (id == null) {
            throw new RuntimeException("Staff ID is required");
        }

        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isEmpty()) {
            throw new RuntimeException("Staff not found");
        }

        Staff staff = staffOptional.get();

        // Check email uniqueness if changed
        if (!staff.getEmail().equals(staffRequestDTO.getEmail()) && 
            staffRepository.existsByEmail(staffRequestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check phone uniqueness if changed
        if (!staff.getPhone().equals(staffRequestDTO.getPhone())) {
            Optional<Staff> existingPhone = staffRepository.findByPhone(staffRequestDTO.getPhone());
            if (existingPhone.isPresent()) {
                throw new RuntimeException("Phone already exists");
            }
        }

        // Update fields
        staff.setName(staffRequestDTO.getName());
        staff.setEmail(staffRequestDTO.getEmail());
        staff.setPhone(staffRequestDTO.getPhone());
        staff.setRole(staffRequestDTO.getRole());
        staff.setStatus(staffRequestDTO.getStatus());

        // Update password if provided
        if (staffRequestDTO.getPassword() != null && !staffRequestDTO.getPassword().isEmpty()) {
            staff.setPassword(passwordEncoder.encode(staffRequestDTO.getPassword()));
        }

        Staff updatedStaff = staffRepository.save(staff);
        return convertToResponseDTO(updatedStaff);
    }

    // Delete Staff
    public void deleteStaff(Long id) {
        if (id == null) {
            throw new RuntimeException("Staff ID is required");
        }

        if (!staffRepository.existsById(id)) {
            throw new RuntimeException("Staff not found");
        }

        staffRepository.deleteById(id);
    }

    // Update Staff Status
    public StaffResponseDTO updateStaffStatus(Long id, StaffStatus status) {
        if (id == null) {
            throw new RuntimeException("Staff ID is required");
        }

        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isEmpty()) {
            throw new RuntimeException("Staff not found");
        }

        Staff staff = staffOptional.get();
        staff.setStatus(status);
        
        Staff updatedStaff = staffRepository.save(staff);
        return convertToResponseDTO(updatedStaff);
    }

    // Helper method to find staff by email or phone
    private Staff findStaffByEmailOrPhone(String emailOrPhone) {
        // Try email first
        Optional<Staff> staffByEmail = staffRepository.findByEmail(emailOrPhone);
        if (staffByEmail.isPresent()) {
            return staffByEmail.get();
        }

        // Try phone
        Optional<Staff> staffByPhone = staffRepository.findByPhone(emailOrPhone);
        return staffByPhone.orElse(null);
    }

    // Helper method to convert entity to DTO
    private StaffResponseDTO convertToResponseDTO(Staff staff) {
        StaffResponseDTO response = new StaffResponseDTO();
        response.setId(staff.getId());
        response.setName(staff.getName());
        response.setEmail(staff.getEmail());
        response.setPhone(staff.getPhone());
        response.setRole(staff.getRole());
        response.setStatus(staff.getStatus());
        response.setCreatedAt(staff.getCreatedAt());
        response.setUpdatedAt(staff.getUpdatedAt());
        return response;
    }
} 