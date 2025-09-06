package com.medsync_hospital.insurance_company_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InsuranceCompanyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InsuranceCompanyServiceApplication.class, args);
    }
} 