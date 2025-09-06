package com.medsync_hospital.hospital_service.dto;

import com.medsync_hospital.hospital_service.enums.HospitalStatus;
import com.medsync_hospital.hospital_service.enums.HospitalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalResponseDTO {
    private Long id;
    private String name;
    private String code;
    private HospitalType type;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phone;
    private String email;
    private String website;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private String description;
    private Integer totalBeds;
    private Integer availableBeds;
    private Integer totalDoctors;
    private Integer totalNurses;
    private String facilities;
    private String specialties;
    private String departments;
    private HospitalStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 