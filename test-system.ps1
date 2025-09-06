# Test script for MedSync Hospital Claim System
Write-Host "Testing MedSync Hospital Claim System..." -ForegroundColor Green
Write-Host "=========================================" -ForegroundColor Green

# Function to test service health
function Test-Service {
    param($ServiceName, $Url, $Port)
    
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:$Port/actuator/health" -TimeoutSec 5 -ErrorAction Stop
        if ($response.StatusCode -eq 200) {
            Write-Host "✓ $ServiceName is running on port $Port" -ForegroundColor Green
            return $true
        }
    }
    catch {
        Write-Host "✗ $ServiceName is not responding on port $Port" -ForegroundColor Red
        return $false
    }
}

# Function to test API endpoints
function Test-API {
    param($ServiceName, $BaseUrl, $Endpoint)
    
    try {
        $response = Invoke-WebRequest -Uri "$BaseUrl$Endpoint" -TimeoutSec 5 -ErrorAction Stop
        Write-Host "✓ $ServiceName API is accessible: $Endpoint" -ForegroundColor Green
        return $true
    }
    catch {
        Write-Host "✗ $ServiceName API is not accessible: $Endpoint" -ForegroundColor Red
        return $false
    }
}

# Test Eureka Server
Write-Host "`nTesting Eureka Server..." -ForegroundColor Yellow
Test-Service "Eureka Server" "http://localhost:8761" "8761"

# Test Config Server
Write-Host "`nTesting Config Server..." -ForegroundColor Yellow
Test-Service "Config Server" "http://localhost:8888" "8888"

# Test API Gateway
Write-Host "`nTesting API Gateway..." -ForegroundColor Yellow
Test-Service "API Gateway" "http://localhost:8080" "8080"

# Test Staff Service
Write-Host "`nTesting Staff Service..." -ForegroundColor Yellow
Test-Service "Staff Service" "http://localhost:8081" "8081"
Test-API "Staff Service" "http://localhost:8081" "/api/staff"

# Test Patient Service
Write-Host "`nTesting Patient Service..." -ForegroundColor Yellow
Test-Service "Patient Service" "http://localhost:8082" "8082"
Test-API "Patient Service" "http://localhost:8082" "/api/patients"

# Test Hospital Service
Write-Host "`nTesting Hospital Service..." -ForegroundColor Yellow
Test-Service "Hospital Service" "http://localhost:8083" "8083"
Test-API "Hospital Service" "http://localhost:8083" "/api/hospitals"

# Test Insurance Company Service
Write-Host "`nTesting Insurance Company Service..." -ForegroundColor Yellow
Test-Service "Insurance Company Service" "http://localhost:8084" "8084"
Test-API "Insurance Company Service" "http://localhost:8084" "/api/insurance-companies"

# Test Claim Service
Write-Host "`nTesting Claim Service..." -ForegroundColor Yellow
Test-Service "Claim Service" "http://localhost:8085" "8085"
Test-API "Claim Service" "http://localhost:8085" "/api/claims"

Write-Host "`n=========================================" -ForegroundColor Green
Write-Host "System Test Complete!" -ForegroundColor Green
Write-Host "=========================================" -ForegroundColor Green 