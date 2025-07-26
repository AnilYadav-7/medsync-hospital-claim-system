#!/bin/bash

echo "🏥 Starting MedSync Hospital Claim System..."
echo "================================================"

# Function to check if a service is running
check_service() {
    local service_name=$1
    local port=$2
    local max_attempts=30
    local attempt=1
    
    echo "⏳ Waiting for $service_name to start on port $port..."
    
    while [ $attempt -le $max_attempts ]; do
        if curl -s http://localhost:$port/actuator/health > /dev/null 2>&1; then
            echo "✅ $service_name is running!"
            return 0
        fi
        echo "   Attempt $attempt/$max_attempts - waiting..."
        sleep 2
        attempt=$((attempt + 1))
    done
    
    echo "❌ $service_name failed to start within expected time"
    return 1
}

# Start Eureka Server
echo "🔧 Starting Eureka Server..."
cd eureka-server
./mvnw spring-boot:run > ../logs/eureka-server.log 2>&1 &
EUREKA_PID=$!
cd ..

# Wait for Eureka Server
sleep 10
if ! curl -s http://localhost:8761 > /dev/null; then
    echo "❌ Eureka Server failed to start"
    exit 1
fi
echo "✅ Eureka Server is running on port 8761"

# Start Patient Service
echo "🏥 Starting Patient Service..."
cd patient-service
./mvnw spring-boot:run > ../logs/patient-service.log 2>&1 &
PATIENT_PID=$!
cd ..

# Wait for Patient Service
check_service "Patient Service" 8081

# Start API Gateway
echo "🚪 Starting API Gateway..."
cd api-gateway
./mvnw spring-boot:run > ../logs/api-gateway.log 2>&1 &
GATEWAY_PID=$!
cd ..

# Wait for API Gateway
check_service "API Gateway" 8080

echo ""
echo "🎉 All services started successfully!"
echo "================================================"
echo "📋 Service URLs:"
echo "   🔧 Eureka Server: http://localhost:8761"
echo "   🚪 API Gateway:   http://localhost:8080"
echo "   🏥 Patient Service: http://localhost:8081"
echo ""
echo "📊 Health Checks:"
echo "   Gateway Health: http://localhost:8080/actuator/health"
echo "   Patient Health: http://localhost:8081/actuator/health"
echo ""
echo "🔍 API Endpoints (via Gateway):"
echo "   GET    /api/patients           - Get all patients"
echo "   POST   /api/patients           - Create patient"
echo "   GET    /api/patients/{id}      - Get patient by ID"
echo "   PUT    /api/patients/{id}      - Update patient"
echo "   DELETE /api/patients/{id}      - Delete patient"
echo "   GET    /api/patients/search?searchTerm=... - Search patients"
echo ""
echo "💾 Database Console (Patient Service):"
echo "   H2 Console: http://localhost:8081/h2-console"
echo "   JDBC URL: jdbc:h2:mem:patientdb"
echo "   Username: sa"
echo "   Password: password"
echo ""
echo "Process IDs:"
echo "   Eureka Server: $EUREKA_PID"
echo "   Patient Service: $PATIENT_PID"
echo "   API Gateway: $GATEWAY_PID"
echo ""
echo "📝 Logs are available in the 'logs' directory"
echo "To stop all services: kill $EUREKA_PID $PATIENT_PID $GATEWAY_PID"