package com.medsync_hospital.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Staff Service Routes
                .route("staff-service", r -> r
                        .path("/api/staff/**")
                        .uri("lb://staff-service"))
                
                // Patient Service Routes
                .route("patient-service", r -> r
                        .path("/api/patients/**")
                        .uri("lb://patient-service"))
                
                // Hospital Service Routes
                .route("hospital-service", r -> r
                        .path("/api/hospitals/**")
                        .uri("lb://hospital-service"))
                
                // Insurance Company Service Routes
                .route("insurance-company-service", r -> r
                        .path("/api/insurance-companies/**")
                        .uri("lb://insurance-company-service"))
                
                // Claim Service Routes
                .route("claim-service", r -> r
                        .path("/api/claims/**")
                        .uri("lb://claim-service"))
                
                .build();
    }
} 