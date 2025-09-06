package com.medsync_hospital.staff_service.dto;

import com.medsync_hospital.staff_service.enums.StaffRole;
import com.medsync_hospital.staff_service.enums.StaffStatus;
import lombok.Data;

@Data
public class StaffRequestDTO {
    private String name;
    private String email;
    private String phone;
    private StaffRole role;
    private String password;
    private StaffStatus status;
} 