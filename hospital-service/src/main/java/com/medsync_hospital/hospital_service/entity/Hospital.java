package com.medsync_hospital.hospital_service.entity;

import java.time.LocalDateTime;

import com.medsync_hospital.hospital_service.enums.HospitalStatus;
import com.medsync_hospital.hospital_service.enums.HospitalType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "hospitals")
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HospitalType type;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    private String contactPerson;

    @Column(nullable = false)
    private String contactPhone;

    @Column(nullable = false)
    private String contactEmail;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer totalBeds;

    @Column(nullable = false)
    private Integer availableBeds;

    @Column(nullable = false)
    private Integer totalDoctors;

    @Column(nullable = false)
    private Integer totalNurses;

    @Column(columnDefinition = "TEXT")
    private String facilities;

    @Column(columnDefinition = "TEXT")
    private String specialties;

    @Column(columnDefinition = "TEXT")
    private String departments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HospitalStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 