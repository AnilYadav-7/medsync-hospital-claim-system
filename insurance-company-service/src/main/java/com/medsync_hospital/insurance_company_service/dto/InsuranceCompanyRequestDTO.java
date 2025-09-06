package com.medsync_hospital.insurance_company_service.dto;

import com.medsync_hospital.insurance_company_service.enums.InsuranceCompanyStatus;
import com.medsync_hospital.insurance_company_service.enums.InsuranceCompanyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceCompanyRequestDTO {
    private String name;
    private String code;
    private InsuranceCompanyType type;
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
    private String licenseNumber;
    private String taxId;
    private String coverageDetails;
    private String policyTypes;
    private String networkHospitals;
    private String claimProcess;
    private String termsAndConditions;
    private InsuranceCompanyStatus status;
} 