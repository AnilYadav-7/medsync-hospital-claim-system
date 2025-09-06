package com.medsync_hospital.staff_service.dto;

import com.medsync_hospital.staff_service.enums.StaffRole;
import com.medsync_hospital.staff_service.enums.StaffStatus;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private Long id;
    private String name;
    private StaffRole role;
    private StaffStatus status;
    private String token; // For future JWT or session token support
} 