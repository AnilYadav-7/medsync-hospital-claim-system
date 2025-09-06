package com.medsync_hospital.claim_service.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.medsync_hospital.claim_service.enums.ClaimStatus;
import com.medsync_hospital.claim_service.enums.ClaimType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "claims")
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String claimNumber;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private Long hospitalId;

    @Column(nullable = false)
    private Long insuranceCompanyId;

    @Column(nullable = false)
    private Long staffId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimStatus status;

    @Column(nullable = false)
    private LocalDate admissionDate;

    @Column(nullable = false)
    private LocalDate dischargeDate;

    @Column(nullable = false)
    private String diagnosis;

    @Column(nullable = false)
    private String treatment;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private BigDecimal coveredAmount;

    @Column(nullable = false)
    private BigDecimal patientResponsibility;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String documents;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(nullable = false)
    private LocalDate claimDate;

    private LocalDate processedDate;
    private LocalDate approvedDate;
    private LocalDate rejectedDate;
    private LocalDate paidDate;

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