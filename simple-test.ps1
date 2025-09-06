Write-Host "Testing MedSync Hospital Claim System..." -ForegroundColor Green

# Test Eureka Server
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8761" -TimeoutSec 5
    Write-Host "✓ Eureka Server is running on port 8761" -ForegroundColor Green
}
catch {
    Write-Host "✗ Eureka Server is not responding on port 8761" -ForegroundColor Red
}

# Test Config Server
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8888" -TimeoutSec 5
    Write-Host "✓ Config Server is running on port 8888" -ForegroundColor Green
}
catch {
    Write-Host "✗ Config Server is not responding on port 8888" -ForegroundColor Red
}

# Test API Gateway
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080" -TimeoutSec 5
    Write-Host "✓ API Gateway is running on port 8080" -ForegroundColor Green
}
catch {
    Write-Host "✗ API Gateway is not responding on port 8080" -ForegroundColor Red
}

# Test Staff Service
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8081/api/staff" -TimeoutSec 5
    Write-Host "✓ Staff Service is running on port 8081" -ForegroundColor Green
}
catch {
    Write-Host "✗ Staff Service is not responding on port 8081" -ForegroundColor Red
}

# Test Patient Service
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8082/api/patients" -TimeoutSec 5
    Write-Host "✓ Patient Service is running on port 8082" -ForegroundColor Green
}
catch {
    Write-Host "✗ Patient Service is not responding on port 8082" -ForegroundColor Red
}

# Test Hospital Service
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8083/api/hospitals" -TimeoutSec 5
    Write-Host "✓ Hospital Service is running on port 8083" -ForegroundColor Green
}
catch {
    Write-Host "✗ Hospital Service is not responding on port 8083" -ForegroundColor Red
}

# Test Insurance Company Service
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8084/api/insurance-companies" -TimeoutSec 5
    Write-Host "✓ Insurance Company Service is running on port 8084" -ForegroundColor Green
}
catch {
    Write-Host "✗ Insurance Company Service is not responding on port 8084" -ForegroundColor Red
}

# Test Claim Service
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8085/api/claims" -TimeoutSec 5
    Write-Host "✓ Claim Service is running on port 8085" -ForegroundColor Green
}
catch {
    Write-Host "✗ Claim Service is not responding on port 8085" -ForegroundColor Red
}

Write-Host "`nTest Complete!" -ForegroundColor Green 