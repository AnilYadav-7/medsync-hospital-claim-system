@echo off
echo Starting MedSync Hospital Claim System...
echo.

echo Starting Config Server...
start "Config Server" cmd /k "cd config-server && mvn spring-boot:run"
timeout /t 10 /nobreak >nul

echo Starting Eureka Server...
start "Eureka Server" cmd /k "cd eureka-server && mvn spring-boot:run"
timeout /t 10 /nobreak >nul

echo Starting API Gateway...
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"
timeout /t 10 /nobreak >nul

echo Starting Staff Service...
start "Staff Service" cmd /k "cd staff-service && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

echo Starting Patient Service...
start "Patient Service" cmd /k "cd patient-service && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

echo Starting Hospital Service...
start "Hospital Service" cmd /k "cd hospital-service && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

echo Starting Insurance Company Service...
start "Insurance Company Service" cmd /k "cd insurance-company-service && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

echo Starting Claim Service...
start "Claim Service" cmd /k "cd claim-service && mvn spring-boot:run"
timeout /t 5 /nobreak >nul

echo.
echo All services are starting...
echo.
echo Access URLs:
echo - Eureka Dashboard: http://localhost:8761
echo - API Gateway: http://localhost:8080
echo - Config Server: http://localhost:8888
echo.
echo Services will be available in a few minutes.
pause 