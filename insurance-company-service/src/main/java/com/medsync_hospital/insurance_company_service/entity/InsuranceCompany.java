package com.medsync_hospital.insurance_company_service.entity;

import java.time.LocalDateTime;

import com.medsync_hospital.insurance_company_service.enums.InsuranceCompanyStatus;
import com.medsync_hospital.insurance_company_service.enums.InsuranceCompanyType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "insurance_companies")
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InsuranceCompanyType type;

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
    private String licenseNumber;

    @Column(nullable = false)
    private String taxId;

    @Column(columnDefinition = "TEXT")
    private String coverageDetails;

    @Column(columnDefinition = "TEXT")
    private String policyTypes;

    @Column(columnDefinition = "TEXT")
    private String networkHospitals;

    @Column(columnDefinition = "TEXT")
    private String claimProcess;

    @Column(columnDefinition = "TEXT")
    private String termsAndConditions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InsuranceCompanyStatus status;

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