package com.medsync_hospital.staff_service.dto;

import com.medsync_hospital.staff_service.enums.StaffRole;
import com.medsync_hospital.staff_service.enums.StaffStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StaffResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private StaffRole role;
    private StaffStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 