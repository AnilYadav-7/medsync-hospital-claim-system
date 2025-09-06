# Comprehensive Test Script for MedSync Hospital Claim System
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "Comprehensive API Testing" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan

# Function to test service health
function Test-ServiceHealth {
    param($ServiceName, $Port)
    
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:$Port/actuator/health" -TimeoutSec 5 -ErrorAction Stop
        if ($response.StatusCode -eq 200) {
            Write-Host "✓ $ServiceName is healthy on port $Port" -ForegroundColor Green
            return $true
        }
    }
    catch {
        Write-Host "✗ $ServiceName health check failed on port $Port" -ForegroundColor Red
        return $false
    }
}

# Function to test API endpoint
function Test-APIEndpoint {
    param($ServiceName, $Url, $Method = "GET", $Body = $null)
    
    try {
        if ($Body) {
            $response = Invoke-RestMethod -Uri $Url -Method $Method -Body ($Body | ConvertTo-Json) -ContentType "application/json" -TimeoutSec 10
        } else {
            $response = Invoke-RestMethod -Uri $Url -Method $Method -TimeoutSec 10
        }
        Write-Host "✓ $ServiceName API test passed: $Method $Url" -ForegroundColor Green
        return $true
    }
    catch {
        Write-Host "✗ $ServiceName API test failed: $Method $Url" -ForegroundColor Red
        Write-Host "  Error: $($_.Exception.Message)" -ForegroundColor Yellow
        return $false
    }
}

# Test 1: Service Health Checks
Write-Host "`n1. Testing Service Health..." -ForegroundColor Yellow
Test-ServiceHealth "Staff Service" "8081"
Test-ServiceHealth "Patient Service" "8082"
Test-ServiceHealth "Hospital Service" "8083"
Test-ServiceHealth "Insurance Company Service" "8084"
Test-ServiceHealth "Claim Service" "8085"

# Test 2: Staff Service APIs
Write-Host "`n2. Testing Staff Service APIs..." -ForegroundColor Yellow
Test-APIEndpoint "Staff Service" "http://localhost:8081/api/staff" "GET"

# Create test staff
$testStaff = @{
    name = "Test Doctor"
    email = "test.doctor@medsync.com"
    phone = "1234567890"
    role = "DOCTOR"
    password = "password123"
    status = "ACTIVE"
}
Test-APIEndpoint "Staff Service" "http://localhost:8081/api/staff" "POST" $testStaff

# Test 3: Patient Service APIs
Write-Host "`n3. Testing Patient Service APIs..." -ForegroundColor Yellow
Test-APIEndpoint "Patient Service" "http://localhost:8082/api/patients" "GET"

# Create test patient
$testPatient = @{
    firstName = "John"
    lastName = "Doe"
    email = "john.doe@email.com"
    phone = "9876543210"
    dateOfBirth = "1990-01-01"
    gender = "MALE"
    address = "123 Test St"
    city = "Test City"
    state = "TS"
    zipCode = "12345"
    emergencyContactName = "Jane Doe"
    emergencyContactPhone = "9876543211"
    bloodGroup = "O_POSITIVE"
    status = "ACTIVE"
}
Test-APIEndpoint "Patient Service" "http://localhost:8082/api/patients" "POST" $testPatient

# Test 4: Hospital Service APIs
Write-Host "`n4. Testing Hospital Service APIs..." -ForegroundColor Yellow
Test-APIEndpoint "Hospital Service" "http://localhost:8083/api/hospitals" "GET"

# Create test hospital
$testHospital = @{
    name = "Test Hospital"
    code = "TH001"
    type = "GENERAL"
    address = "456 Test Ave"
    city = "Test City"
    state = "TS"
    zipCode = "12345"
    country = "USA"
    phone = "5551234567"
    email = "info@testhospital.com"
    website = "www.testhospital.com"
    contactPerson = "Dr. Test"
    contactPhone = "5551234568"
    contactEmail = "contact@testhospital.com"
    totalBeds = 100
    availableBeds = 50
    totalDoctors = 20
    totalNurses = 40
    status = "ACTIVE"
}
Test-APIEndpoint "Hospital Service" "http://localhost:8083/api/hospitals" "POST" $testHospital

# Test 5: Insurance Company Service APIs
Write-Host "`n5. Testing Insurance Company Service APIs..." -ForegroundColor Yellow
Test-APIEndpoint "Insurance Company Service" "http://localhost:8084/api/insurance-companies" "GET"

# Create test insurance company
$testInsurance = @{
    name = "Test Insurance Co"
    code = "TIC001"
    type = "HEALTH"
    address = "789 Insurance St"
    city = "Insurance City"
    state = "IC"
    zipCode = "67890"
    country = "USA"
    phone = "5559876543"
    email = "info@testinsurance.com"
    website = "www.testinsurance.com"
    contactPerson = "Insurance Manager"
    contactPhone = "5559876544"
    contactEmail = "manager@testinsurance.com"
    licenseNumber = "LIC123456"
    taxId = "TAX789012"
    status = "ACTIVE"
}
Test-APIEndpoint "Insurance Company Service" "http://localhost:8084/api/insurance-companies" "POST" $testInsurance

# Test 6: Claim Service APIs
Write-Host "`n6. Testing Claim Service APIs..." -ForegroundColor Yellow
Test-APIEndpoint "Claim Service" "http://localhost:8085/api/claims" "GET"

# Create test claim
$testClaim = @{
    claimNumber = "CLM001"
    patientId = 1
    hospitalId = 1
    insuranceCompanyId = 1
    staffId = 1
    type = "INPATIENT"
    status = "PENDING"
    admissionDate = "2024-01-01"
    dischargeDate = "2024-01-05"
    diagnosis = "Test diagnosis"
    treatment = "Test treatment"
    totalAmount = 10000.00
    coveredAmount = 8000.00
    patientResponsibility = 2000.00
    claimDate = "2024-01-06"
}
Test-APIEndpoint "Claim Service" "http://localhost:8085/api/claims" "POST" $testClaim

# Test 7: API Gateway Routing
Write-Host "`n7. Testing API Gateway Routing..." -ForegroundColor Yellow
Test-APIEndpoint "API Gateway" "http://localhost:8080/api/staff" "GET"
Test-APIEndpoint "API Gateway" "http://localhost:8080/api/patients" "GET"
Test-APIEndpoint "API Gateway" "http://localhost:8080/api/hospitals" "GET"
Test-APIEndpoint "API Gateway" "http://localhost:8080/api/insurance-companies" "GET"
Test-APIEndpoint "API Gateway" "http://localhost:8080/api/claims" "GET"

Write-Host "`n===============================================" -ForegroundColor Cyan
Write-Host "Comprehensive Testing Complete!" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan 