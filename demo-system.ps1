Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "MedSync Hospital Claim System - DEMO" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan

# Function to make API calls
function Invoke-APICall {
    param($Method, $Url, $Body)
    
    try {
        if ($Body) {
            $response = Invoke-RestMethod -Uri $Url -Method $Method -Body ($Body | ConvertTo-Json) -ContentType "application/json" -TimeoutSec 10
        } else {
            $response = Invoke-RestMethod -Uri $Url -Method $Method -TimeoutSec 10
        }
        return $response
    }
    catch {
        Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
        return $null
    }
}

Write-Host "`n1. Creating Staff Members..." -ForegroundColor Yellow

# Create Staff Members
$staff1 = @{
    name = "Dr. John Smith"
    email = "john.smith@medsync.com"
    phone = "1234567890"
    role = "DOCTOR"
    password = "password123"
    status = "ACTIVE"
}

$staff2 = @{
    name = "Nurse Sarah Johnson"
    email = "sarah.johnson@medsync.com"
    phone = "9876543210"
    role = "NURSE"
    password = "password123"
    status = "ACTIVE"
}

$staffResponse1 = Invoke-APICall -Method "POST" -Url "http://localhost:8081/api/staff" -Body $staff1
$staffResponse2 = Invoke-APICall -Method "POST" -Url "http://localhost:8081/api/staff" -Body $staff2

if ($staffResponse1) {
    Write-Host "✓ Staff 1 created: $($staffResponse1.name)" -ForegroundColor Green
}
if ($staffResponse2) {
    Write-Host "✓ Staff 2 created: $($staffResponse2.name)" -ForegroundColor Green
}

Write-Host "`n2. Creating Patients..." -ForegroundColor Yellow

# Create Patients
$patient1 = @{
    firstName = "Alice"
    lastName = "Johnson"
    dateOfBirth = "1985-03-15"
    gender = "FEMALE"
    bloodGroup = "A_POSITIVE"
    phone = "5551234567"
    email = "alice.johnson@email.com"
    address = "123 Main St, City"
    emergencyContact = "Bob Johnson"
    emergencyPhone = "5559876543"
    insuranceProvider = "HealthCare Plus"
    insuranceNumber = "HC123456789"
    medicalHistory = "Hypertension, Diabetes"
    allergies = "Penicillin"
    status = "ACTIVE"
}

$patient2 = @{
    firstName = "Bob"
    lastName = "Wilson"
    dateOfBirth = "1978-07-22"
    gender = "MALE"
    bloodGroup = "O_POSITIVE"
    phone = "5559876543"
    email = "bob.wilson@email.com"
    address = "456 Oak Ave, Town"
    emergencyContact = "Carol Wilson"
    emergencyPhone = "5551234567"
    insuranceProvider = "MediCare"
    insuranceNumber = "MC987654321"
    medicalHistory = "Asthma"
    allergies = "None"
    status = "ACTIVE"
}

$patientResponse1 = Invoke-APICall -Method "POST" -Url "http://localhost:8082/api/patients" -Body $patient1
$patientResponse2 = Invoke-APICall -Method "POST" -Url "http://localhost:8082/api/patients" -Body $patient2

if ($patientResponse1) {
    Write-Host "✓ Patient 1 created: $($patientResponse1.firstName) $($patientResponse1.lastName)" -ForegroundColor Green
}
if ($patientResponse2) {
    Write-Host "✓ Patient 2 created: $($patientResponse2.firstName) $($patientResponse2.lastName)" -ForegroundColor Green
}

Write-Host "`n3. Creating Hospitals..." -ForegroundColor Yellow

# Create Hospitals
$hospital1 = @{
    name = "MedSync General Hospital"
    code = "MSGH001"
    type = "GENERAL"
    address = "789 Hospital Blvd"
    city = "Metro City"
    state = "MC"
    zipCode = "12345"
    country = "USA"
    phone = "5551112222"
    email = "info@msgh.com"
    website = "www.msgh.com"
    contactPerson = "Dr. Admin"
    contactPhone = "5551113333"
    contactEmail = "admin@msgh.com"
    description = "Leading general hospital"
    totalBeds = 500
    availableBeds = 150
    totalDoctors = 75
    totalNurses = 200
    facilities = "ICU, Emergency, Surgery, Radiology"
    specialties = "Cardiology, Neurology, Orthopedics"
    departments = "Emergency, Surgery, Pediatrics, ICU"
    status = "ACTIVE"
}

$hospitalResponse1 = Invoke-APICall -Method "POST" -Url "http://localhost:8083/api/hospitals" -Body $hospital1

if ($hospitalResponse1) {
    Write-Host "✓ Hospital created: $($hospitalResponse1.name)" -ForegroundColor Green
}

Write-Host "`n4. Creating Insurance Companies..." -ForegroundColor Yellow

# Create Insurance Company
$insurance1 = @{
    name = "HealthCare Plus Insurance"
    code = "HCPI001"
    type = "HEALTH"
    address = "456 Insurance Ave"
    city = "Insurance City"
    state = "IC"
    zipCode = "67890"
    country = "USA"
    phone = "5554443333"
    email = "info@hcpi.com"
    website = "www.hcpi.com"
    contactPerson = "Insurance Manager"
    contactPhone = "5554444444"
    contactEmail = "manager@hcpi.com"
    description = "Comprehensive health insurance"
    licenseNumber = "LIC123456"
    taxId = "TAX789012"
    coverageDetails = "Comprehensive health coverage"
    policyTypes = "Individual, Family, Group"
    networkHospitals = "MedSync General Hospital"
    claimProcess = "Online submission with 24-hour processing"
    termsAndConditions = "Standard health insurance terms"
    status = "ACTIVE"
}

$insuranceResponse1 = Invoke-APICall -Method "POST" -Url "http://localhost:8084/api/insurance-companies" -Body $insurance1

if ($insuranceResponse1) {
    Write-Host "✓ Insurance Company created: $($insuranceResponse1.name)" -ForegroundColor Green
}

Write-Host "`n5. Creating Claims..." -ForegroundColor Yellow

# Create Claims
$claim1 = @{
    claimNumber = "CLM2024001"
    patientId = 1
    hospitalId = 1
    insuranceCompanyId = 1
    staffId = 1
    type = "INPATIENT"
    status = "SUBMITTED"
    admissionDate = "2024-01-15"
    dischargeDate = "2024-01-20"
    diagnosis = "Acute appendicitis"
    treatment = "Laparoscopic appendectomy"
    totalAmount = 15000.00
    coveredAmount = 12000.00
    patientResponsibility = 3000.00
    description = "Emergency appendectomy procedure"
    documents = "Medical reports, X-rays, Lab results"
    notes = "Patient responded well to treatment"
    claimDate = "2024-01-21"
}

$claimResponse1 = Invoke-APICall -Method "POST" -Url "http://localhost:8085/api/claims" -Body $claim1

if ($claimResponse1) {
    Write-Host "✓ Claim created: $($claimResponse1.claimNumber)" -ForegroundColor Green
}

Write-Host "`n6. Retrieving All Data..." -ForegroundColor Yellow

# Get all staff
$allStaff = Invoke-APICall -Method "GET" -Url "http://localhost:8081/api/staff"
if ($allStaff) {
    Write-Host "✓ Retrieved $($allStaff.Count) staff members" -ForegroundColor Green
}

# Get all patients
$allPatients = Invoke-APICall -Method "GET" -Url "http://localhost:8082/api/patients"
if ($allPatients) {
    Write-Host "✓ Retrieved $($allPatients.Count) patients" -ForegroundColor Green
}

# Get all hospitals
$allHospitals = Invoke-APICall -Method "GET" -Url "http://localhost:8083/api/hospitals"
if ($allHospitals) {
    Write-Host "✓ Retrieved $($allHospitals.Count) hospitals" -ForegroundColor Green
}

# Get all insurance companies
$allInsurance = Invoke-APICall -Method "GET" -Url "http://localhost:8084/api/insurance-companies"
if ($allInsurance) {
    Write-Host "✓ Retrieved $($allInsurance.Count) insurance companies" -ForegroundColor Green
}

# Get all claims
$allClaims = Invoke-APICall -Method "GET" -Url "http://localhost:8085/api/claims"
if ($allClaims) {
    Write-Host "✓ Retrieved $($allClaims.Count) claims" -ForegroundColor Green
}

Write-Host "`n===============================================" -ForegroundColor Cyan
Write-Host "DEMO COMPLETED SUCCESSFULLY!" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan

Write-Host "`nSystem URLs:" -ForegroundColor Yellow
Write-Host "- Eureka Dashboard: http://localhost:8761" -ForegroundColor White
Write-Host "- API Gateway: http://localhost:8080" -ForegroundColor White
Write-Host "- Staff Service: http://localhost:8081" -ForegroundColor White
Write-Host "- Patient Service: http://localhost:8082" -ForegroundColor White
Write-Host "- Hospital Service: http://localhost:8083" -ForegroundColor White
Write-Host "- Insurance Service: http://localhost:8084" -ForegroundColor White
Write-Host "- Claim Service: http://localhost:8085" -ForegroundColor White 