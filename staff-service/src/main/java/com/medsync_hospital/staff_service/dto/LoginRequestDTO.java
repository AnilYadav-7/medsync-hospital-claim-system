package com.medsync_hospital.staff_service.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String emailOrPhone;
    private String password;
} 