# MedSync Hospital Claim System

A comprehensive microservices-based hospital claim management system built with Spring Boot, Spring Cloud, and MySQL.

## 🏥 Project Overview

MedSync Hospital Claim System is a robust, scalable microservices architecture designed to manage hospital operations, patient care, insurance claims, and staff management. The system provides comprehensive APIs for all aspects of hospital claim processing and management.

## 🏗️ Architecture

### Microservices Architecture
- **Staff Service** (Port: 8081) - Staff management and authentication
- **Patient Service** (Port: 8082) - Patient registration and management
- **Hospital Service** (Port: 8083) - Hospital and facility management
- **Insurance Company Service** (Port: 8084) - Insurance company management
- **Claim Service** (Port: 8085) - Insurance claim processing
- **API Gateway** (Port: 8080) - Centralized API routing and load balancing
- **Eureka Server** (Port: 8761) - Service discovery and registration
- **Config Server** (Port: 8888) - Centralized configuration management

## 🚀 Features

### Staff Management
- ✅ Staff registration and authentication
- ✅ Role-based access control (ADMIN, DOCTOR, NURSE, RECEPTIONIST, LAB_TECHNICIAN)
- ✅ Staff status management (ACTIVE, INACTIVE)
- ✅ Comprehensive staff search and filtering

### Patient Management
- ✅ Patient registration with complete medical history
- ✅ Blood group and gender classification
- ✅ Emergency contact management
- ✅ Medical history tracking
- ✅ Patient status management (ACTIVE, INACTIVE, DISCHARGED, DECEASED)
- ✅ Advanced search and filtering capabilities

### Hospital Management
- ✅ Hospital registration and configuration
- ✅ Hospital type classification (GENERAL, SPECIALTY, TEACHING, RESEARCH, etc.)
- ✅ Bed availability tracking
- ✅ Staff capacity management
- ✅ Facility and specialty management
- ✅ Hospital status management (ACTIVE, INACTIVE, MAINTENANCE, CLOSED)

### Insurance Company Management
- ✅ Insurance company registration
- ✅ Company type classification (HEALTH, LIFE, AUTO, PROPERTY, etc.)
- ✅ License and tax ID management
- ✅ Network hospital management
- ✅ Coverage details and policy types
- ✅ Company status management (ACTIVE, INACTIVE, SUSPENDED, TERMINATED)

### Claim Management
- ✅ Comprehensive claim processing
- ✅ Claim type classification (INPATIENT, OUTPATIENT, EMERGENCY, SURGERY, etc.)
- ✅ Claim status tracking (PENDING, SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED, PAID, CANCELLED)
- ✅ Financial calculations (total amount, covered amount, patient responsibility)
- ✅ Document management
- ✅ Advanced search and reporting

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.4
- **Cloud**: Spring Cloud 2025.0.0
- **Database**: MySQL 8.0.33
- **ORM**: Spring Data JPA
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Configuration**: Spring Cloud Config
- **Build Tool**: Maven
- **Language**: Java 17
- **Lombok**: For reducing boilerplate code

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- Git

## 🚀 Quick Start

### 1. Database Setup

Create the MySQL database:
```sql
CREATE DATABASE medsync_hospital;
CREATE DATABASE medsync_hospital_dbs;
```

### 2. Clone and Build

```bash
git clone <repository-url>
cd medsync-hospital-claim-system
mvn clean install
```

### 3. Start Services

Start the services in the following order:

#### 1. Config Server
```bash
cd config-server
mvn spring-boot:run
```

#### 2. Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```

#### 3. API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```

#### 4. Microservices (in any order)
```bash
# Staff Service
cd staff-service
mvn spring-boot:run

# Patient Service
cd patient-service
mvn spring-boot:run

# Hospital Service
cd hospital-service
mvn spring-boot:run

# Insurance Company Service
cd insurance-company-service
mvn spring-boot:run

# Claim Service
cd claim-service
mvn spring-boot:run
```

### 4. Access the Application

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Config Server**: http://localhost:8888

## 📚 API Documentation

### Staff Service APIs
- `POST /api/staff` - Create staff
- `GET /api/staff` - Get all staff
- `GET /api/staff/{id}` - Get staff by ID
- `PUT /api/staff/{id}` - Update staff
- `DELETE /api/staff/{id}` - Delete staff
- `POST /api/staff/login` - Staff login
- `GET /api/staff/role/{role}` - Get staff by role
- `GET /api/staff/status/{status}` - Get staff by status

### Patient Service APIs
- `POST /api/patients` - Create patient
- `GET /api/patients` - Get all patients
- `GET /api/patients/{id}` - Get patient by ID
- `PUT /api/patients/{id}` - Update patient
- `DELETE /api/patients/{id}` - Delete patient
- `GET /api/patients/search?name={name}` - Search patients by name
- `GET /api/patients/gender/{gender}` - Get patients by gender
- `GET /api/patients/blood-group/{bloodGroup}` - Get patients by blood group
- `GET /api/patients/status/{status}` - Get patients by status

### Hospital Service APIs
- `POST /api/hospitals` - Create hospital
- `GET /api/hospitals` - Get all hospitals
- `GET /api/hospitals/{id}` - Get hospital by ID
- `PUT /api/hospitals/{id}` - Update hospital
- `DELETE /api/hospitals/{id}` - Delete hospital
- `GET /api/hospitals/search?name={name}` - Search hospitals by name
- `GET /api/hospitals/type/{type}` - Get hospitals by type
- `GET /api/hospitals/status/{status}` - Get hospitals by status
- `GET /api/hospitals/available-beds` - Get hospitals with available beds

### Insurance Company Service APIs
- `POST /api/insurance-companies` - Create insurance company
- `GET /api/insurance-companies` - Get all insurance companies
- `GET /api/insurance-companies/{id}` - Get insurance company by ID
- `PUT /api/insurance-companies/{id}` - Update insurance company
- `DELETE /api/insurance-companies/{id}` - Delete insurance company
- `GET /api/insurance-companies/search?name={name}` - Search insurance companies by name
- `GET /api/insurance-companies/type/{type}` - Get insurance companies by type
- `GET /api/insurance-companies/status/{status}` - Get insurance companies by status
- `GET /api/insurance-companies/network-hospital?hospitalName={name}` - Get insurance companies by network hospital

### Claim Service APIs
- `POST /api/claims` - Create claim
- `GET /api/claims` - Get all claims
- `GET /api/claims/{id}` - Get claim by ID
- `PUT /api/claims/{id}` - Update claim
- `DELETE /api/claims/{id}` - Delete claim
- `GET /api/claims/claim-number/{claimNumber}` - Get claim by claim number
- `GET /api/claims/patient/{patientId}` - Get claims by patient ID
- `GET /api/claims/hospital/{hospitalId}` - Get claims by hospital ID
- `GET /api/claims/insurance-company/{insuranceCompanyId}` - Get claims by insurance company ID
- `GET /api/claims/staff/{staffId}` - Get claims by staff ID
- `GET /api/claims/type/{type}` - Get claims by type
- `GET /api/claims/status/{status}` - Get claims by status
- `GET /api/claims/claim-date-range?startDate={date}&endDate={date}` - Get claims by claim date range
- `GET /api/claims/admission-date-range?startDate={date}&endDate={date}` - Get claims by admission date range
- `GET /api/claims/discharge-date-range?startDate={date}&endDate={date}` - Get claims by discharge date range
- `GET /api/claims/total-amount?minAmount={amount}` - Get claims by total amount
- `GET /api/claims/covered-amount?minAmount={amount}` - Get claims by covered amount
- `GET /api/claims/patient-responsibility?minAmount={amount}` - Get claims by patient responsibility
- `GET /api/claims/search/diagnosis?diagnosis={diagnosis}` - Search claims by diagnosis
- `GET /api/claims/search/treatment?treatment={treatment}` - Search claims by treatment

## 🔧 Configuration

### Database Configuration
Update the database configuration in each service's `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medsync_hospital
spring.datasource.username=root
spring.datasource.password=Anil@123
```

### Service Ports
- Eureka Server: 8761
- Config Server: 8888
- API Gateway: 8080
- Staff Service: 8081
- Patient Service: 8082
- Hospital Service: 8083
- Insurance Company Service: 8084
- Claim Service: 8085

## 🧪 Testing

### Sample API Requests

#### Create Staff
```bash
curl -X POST http://localhost:8080/api/staff \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Dr. John Doe",
    "email": "john.doe@hospital.com",
    "phone": "+1234567890",
    "role": "DOCTOR",
    "password": "password123",
    "status": "ACTIVE"
  }'
```

#### Create Patient
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@email.com",
    "phone": "+1234567891",
    "dateOfBirth": "1990-01-01",
    "gender": "FEMALE",
    "address": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "emergencyContactName": "John Smith",
    "emergencyContactPhone": "+1234567892",
    "bloodGroup": "A_POSITIVE",
    "status": "ACTIVE"
  }'
```

#### Create Hospital
```bash
curl -X POST http://localhost:8080/api/hospitals \
  -H "Content-Type: application/json" \
  -d '{
    "name": "MedSync General Hospital",
    "code": "MSGH001",
    "type": "GENERAL",
    "address": "456 Hospital Ave",
    "city": "New York",
    "state": "NY",
    "zipCode": "10002",
    "country": "USA",
    "phone": "+1234567893",
    "email": "info@medsync.com",
    "website": "https://medsync.com",
    "contactPerson": "Dr. Admin",
    "contactPhone": "+1234567894",
    "contactEmail": "admin@medsync.com",
    "totalBeds": 500,
    "availableBeds": 450,
    "totalDoctors": 100,
    "totalNurses": 200,
    "status": "ACTIVE"
  }'
```

#### Create Insurance Company
```bash
curl -X POST http://localhost:8080/api/insurance-companies \
  -H "Content-Type: application/json" \
  -d '{
    "name": "HealthCare Insurance",
    "code": "HCI001",
    "type": "HEALTH",
    "address": "789 Insurance Blvd",
    "city": "New York",
    "state": "NY",
    "zipCode": "10003",
    "country": "USA",
    "phone": "+1234567895",
    "email": "info@healthcare.com",
    "website": "https://healthcare.com",
    "contactPerson": "John Manager",
    "contactPhone": "+1234567896",
    "contactEmail": "manager@healthcare.com",
    "licenseNumber": "LIC123456",
    "taxId": "TAX789012",
    "status": "ACTIVE"
  }'
```

#### Create Claim
```bash
curl -X POST http://localhost:8080/api/claims \
  -H "Content-Type: application/json" \
  -d '{
    "claimNumber": "CLM001",
    "patientId": 1,
    "hospitalId": 1,
    "insuranceCompanyId": 1,
    "staffId": 1,
    "type": "INPATIENT",
    "status": "PENDING",
    "admissionDate": "2024-01-01",
    "dischargeDate": "2024-01-05",
    "diagnosis": "Appendicitis",
    "treatment": "Appendectomy",
    "totalAmount": 15000.00,
    "coveredAmount": 12000.00,
    "patientResponsibility": 3000.00,
    "claimDate": "2024-01-06"
  }'
```

## 🔒 Security Features

- Role-based access control
- Password encryption
- Input validation
- Error handling
- Audit logging

## 📊 Monitoring

- Eureka Dashboard for service discovery
- Spring Boot Actuator endpoints
- Application metrics
- Health checks

## 🚀 Deployment

### Docker Deployment
```bash
# Build Docker images
docker build -t medsync-staff-service staff-service/
docker build -t medsync-patient-service patient-service/
docker build -t medsync-hospital-service hospital-service/
docker build -t medsync-insurance-service insurance-company-service/
docker build -t medsync-claim-service claim-service/
docker build -t medsync-api-gateway api-gateway/
docker build -t medsync-eureka-server eureka-server/
docker build -t medsync-config-server config-server/

# Run with Docker Compose
docker-compose up -d
```

### Production Deployment
- Use external MySQL database
- Configure proper security settings
- Set up monitoring and logging
- Use load balancers
- Implement proper backup strategies

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 📞 Support

For support and questions, please contact the development team.

---

**MedSync Hospital Claim System** - A comprehensive solution for modern healthcare management. 