# 🏥 MedSync Hospital Claim System

A comprehensive microservices-based hospital claim management system built with Spring Boot and Spring Cloud.

## 🏗️ Architecture

This system follows a microservices architecture with service discovery and API gateway patterns:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client/UI     │    │   API Gateway   │    │  Eureka Server  │
│                 │    │   (Port 8080)   │    │   (Port 8761)   │
│                 │────│                 │────│                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                                │
                       ┌─────────────────┐
                       │ Patient Service │
                       │   (Port 8081)   │
                       │                 │
                       └─────────────────┘
```

## 🚀 Services

### 1. **Eureka Server** (Port 8761)
- **Purpose**: Service discovery and registration
- **Technology**: Netflix Eureka
- **URL**: http://localhost:8761

### 2. **API Gateway** (Port 8080)
- **Purpose**: Single entry point, routing, load balancing
- **Technology**: Spring Cloud Gateway
- **URL**: http://localhost:8080

### 3. **Patient Service** (Port 8081)
- **Purpose**: Patient management (CRUD operations)
- **Technology**: Spring Boot + JPA + H2 Database
- **URL**: http://localhost:8081

## 📋 Features

### Patient Management
- ✅ Create, Read, Update, Delete patients
- ✅ Search patients by name, email
- ✅ Patient validation (email, phone, insurance number)
- ✅ Comprehensive patient information storage
- ✅ Audit trails (created/updated timestamps)

### System Features
- ✅ Service discovery with Eureka
- ✅ API Gateway routing
- ✅ Health monitoring
- ✅ In-memory H2 database
- ✅ RESTful API design
- ✅ Input validation
- ✅ Error handling

## 🛠️ Technology Stack

- **Java**: 17
- **Spring Boot**: 3.5.4
- **Spring Cloud**: 2024.0.0
- **Database**: H2 (in-memory)
- **Build Tool**: Maven
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- curl (for testing)

### 1. Start All Services
```bash
./start-services.sh
```

This script will:
1. Start Eureka Server (8761)
2. Start Patient Service (8081)
3. Start API Gateway (8080)
4. Wait for all services to be healthy

### 2. Test the APIs
```bash
./test-api.sh
```

This will run comprehensive tests on all API endpoints.

### 3. Manual Testing

#### Health Check
```bash
curl http://localhost:8080/actuator/health
```

#### Get All Patients
```bash
curl http://localhost:8080/api/patients
```

#### Create a Patient
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@email.com",
    "phoneNumber": "+1234567890",
    "dateOfBirth": "1990-01-15",
    "gender": "MALE",
    "address": "123 Main St, Anytown, USA",
    "insuranceNumber": "INS123456"
  }'
```

#### Search Patients
```bash
curl "http://localhost:8080/api/patients/search?searchTerm=John"
```

## 📊 API Endpoints

### Patient Service (via API Gateway)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/patients` | Get all patients |
| POST | `/api/patients` | Create a new patient |
| GET | `/api/patients/{id}` | Get patient by ID |
| PUT | `/api/patients/{id}` | Update patient |
| DELETE | `/api/patients/{id}` | Delete patient |
| GET | `/api/patients/email/{email}` | Get patient by email |
| GET | `/api/patients/insurance/{number}` | Get patient by insurance number |
| GET | `/api/patients/search?searchTerm=...` | Search patients |
| GET | `/api/patients/{id}/exists` | Check if patient exists |

### System Endpoints

| Service | Endpoint | Description |
|---------|----------|-------------|
| API Gateway | `http://localhost:8080/actuator/health` | Gateway health |
| Patient Service | `http://localhost:8081/actuator/health` | Patient service health |
| Eureka Server | `http://localhost:8761` | Service registry dashboard |

## 💾 Database

### H2 Console Access
- **URL**: http://localhost:8081/h2-console
- **JDBC URL**: `jdbc:h2:mem:patientdb`
- **Username**: `sa`
- **Password**: `password`

### Patient Entity Structure
```sql
CREATE TABLE patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(255),
    date_of_birth DATE,
    gender VARCHAR(10),
    address VARCHAR(500),
    insurance_number VARCHAR(255) UNIQUE,
    emergency_contact_name VARCHAR(255),
    emergency_contact_phone VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

## 🔧 Development

### Build Individual Services
```bash
# Build all services
cd api-gateway && ./mvnw clean compile
cd ../eureka-server && ./mvnw clean compile  
cd ../patient-service && ./mvnw clean compile
```

### Run Individual Services
```bash
# Start Eureka Server
cd eureka-server && ./mvnw spring-boot:run

# Start Patient Service (in another terminal)
cd patient-service && ./mvnw spring-boot:run

# Start API Gateway (in another terminal)
cd api-gateway && ./mvnw spring-boot:run
```

## 📝 Logs

Service logs are stored in the `logs/` directory:
- `logs/eureka-server.log`
- `logs/patient-service.log`
- `logs/api-gateway.log`

## 🛑 Stopping Services

To stop all services, use the process IDs displayed when starting:
```bash
kill <EUREKA_PID> <PATIENT_PID> <GATEWAY_PID>
```

Or find and kill Java processes:
```bash
pkill -f "spring-boot:run"
```

## 🔮 Future Enhancements

### Planned Features
- [ ] Claim Management Service
- [ ] Doctor/Provider Service  
- [ ] Insurance Service
- [ ] Authentication & Authorization
- [ ] Claims Processing Workflow
- [ ] Notification Service
- [ ] Reporting & Analytics
- [ ] Docker containerization
- [ ] Kubernetes deployment
- [ ] Database persistence (PostgreSQL/MySQL)

### Additional Microservices Architecture
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Web Portal    │    │   API Gateway   │    │  Eureka Server  │
│     (React)     │────│   (Port 8080)   │────│   (Port 8761)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
       ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
       │ Patient Service │ │  Claim Service  │ │ Doctor Service  │
       │   (Port 8081)   │ │   (Port 8082)   │ │   (Port 8083)   │
       └─────────────────┘ └─────────────────┘ └─────────────────┘
                │               │               │
       ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
       │   Patient DB    │ │    Claim DB     │ │   Doctor DB     │
       │     (H2/PG)     │ │     (H2/PG)     │ │     (H2/PG)     │
       └─────────────────┘ └─────────────────┘ └─────────────────┘
```

## 📞 Support

For issues or questions:
1. Check the logs in the `logs/` directory
2. Verify all services are running on their expected ports
3. Ensure Java 17+ is installed
4. Check that ports 8080, 8081, and 8761 are available

## 📄 License

This project is for educational and demonstration purposes.