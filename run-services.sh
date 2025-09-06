#!/bin/bash

echo "Starting MedSync Hospital Claim System..."
echo

echo "Starting Config Server..."
cd config-server && mvn spring-boot:run &
CONFIG_PID=$!
sleep 10

echo "Starting Eureka Server..."
cd ../eureka-server && mvn spring-boot:run &
EUREKA_PID=$!
sleep 10

echo "Starting API Gateway..."
cd ../api-gateway && mvn spring-boot:run &
GATEWAY_PID=$!
sleep 10

echo "Starting Staff Service..."
cd ../staff-service && mvn spring-boot:run &
STAFF_PID=$!
sleep 5

echo "Starting Patient Service..."
cd ../patient-service && mvn spring-boot:run &
PATIENT_PID=$!
sleep 5

echo "Starting Hospital Service..."
cd ../hospital-service && mvn spring-boot:run &
HOSPITAL_PID=$!
sleep 5

echo "Starting Insurance Company Service..."
cd ../insurance-company-service && mvn spring-boot:run &
INSURANCE_PID=$!
sleep 5

echo "Starting Claim Service..."
cd ../claim-service && mvn spring-boot:run &
CLAIM_PID=$!
sleep 5

echo
echo "All services are starting..."
echo
echo "Access URLs:"
echo "- Eureka Dashboard: http://localhost:8761"
echo "- API Gateway: http://localhost:8080"
echo "- Config Server: http://localhost:8888"
echo
echo "Services will be available in a few minutes."
echo "Press Ctrl+C to stop all services"

# Wait for user to stop
wait 