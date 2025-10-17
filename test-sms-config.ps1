# SMS Configuration Test Script for MedSync Hospital System (PowerShell)
# This script tests the SMS functionality with your configured providers

# Configuration
$BaseUrl = "http://localhost:8081"
$TestPhone = "+919100784590"  # Your mobile number for testing

Write-Host "=== MedSync Hospital SMS Configuration Test ===" -ForegroundColor Yellow
Write-Host ""

# Function to test API endpoint
function Test-Endpoint {
    param(
        [string]$Endpoint,
        [string]$Data,
        [string]$Description
    )
    
    Write-Host "Testing: $Description" -ForegroundColor Yellow
    Write-Host "Endpoint: $Endpoint"
    Write-Host "Data: $Data"
    Write-Host ""
    
    try {
        $headers = @{
            "Content-Type" = "application/json"
        }
        
        $response = Invoke-RestMethod -Uri "$BaseUrl$Endpoint" -Method Post -Body $Data -Headers $headers
        
        Write-Host "✓ Success" -ForegroundColor Green
        Write-Host "Response: $($response | ConvertTo-Json -Compress)"
    }
    catch {
        Write-Host "✗ Failed" -ForegroundColor Red
        Write-Host "Error: $($_.Exception.Message)"
        if ($_.Exception.Response) {
            $statusCode = $_.Exception.Response.StatusCode
            Write-Host "HTTP Status: $statusCode" -ForegroundColor Red
        }
    }
    
    Write-Host ""
    Write-Host "---"
    Write-Host ""
}

# Check if staff service is running
Write-Host "Checking if Staff Service is running..." -ForegroundColor Yellow
try {
    $healthCheck = Invoke-RestMethod -Uri "$BaseUrl/actuator/health" -Method Get -TimeoutSec 5
    Write-Host "✓ Staff Service is running" -ForegroundColor Green
}
catch {
    Write-Host "✗ Staff Service is not running or not accessible" -ForegroundColor Red
    Write-Host "Please start the staff service first:"
    Write-Host "cd staff-service && mvn spring-boot:run"
    exit 1
}

Write-Host ""
Write-Host "---"
Write-Host ""

# Test 1: Send OTP
Test-Endpoint "/api/sms/send-otp" `
    "{`"phoneNumber`": `"$TestPhone`"}" `
    "Send OTP to $TestPhone"

# Wait a moment before next test
Start-Sleep -Seconds 2

# Test 2: Send Custom SMS
Test-Endpoint "/api/sms/send" `
    "{`"phoneNumber`": `"$TestPhone`", `"message`": `"Test SMS from MedSync Hospital System`"}" `
    "Send Custom SMS to $TestPhone"

# Wait a moment before next test
Start-Sleep -Seconds 2

# Test 3: Resend OTP (should be rate limited)
Test-Endpoint "/api/sms/resend-otp" `
    "{`"phoneNumber`": `"$TestPhone`"}" `
    "Resend OTP (should be rate limited)"

# Test 4: Verify OTP (with user input)
Write-Host "Testing OTP Verification" -ForegroundColor Yellow
$userOtp = Read-Host "Please enter the OTP you received (or press Enter to skip)"

if ($userOtp) {
    Test-Endpoint "/api/sms/verify-otp" `
        "{`"phoneNumber`": `"$TestPhone`", `"otp`": `"$userOtp`"}" `
        "Verify OTP: $userOtp"
} else {
    Write-Host "Skipping OTP verification test" -ForegroundColor Yellow
    Write-Host ""
}

# Test 5: Invalid phone number
Test-Endpoint "/api/sms/send-otp" `
    "{`"phoneNumber`": `"invalid-phone`"}" `
    "Send OTP to invalid phone number (should fail)"

# Test 6: Empty message
Test-Endpoint "/api/sms/send" `
    "{`"phoneNumber`": `"$TestPhone`", `"message`": `"`"}" `
    "Send empty message (should fail)"

Write-Host "=== Test Summary ===" -ForegroundColor Yellow
Write-Host ""
Write-Host "1. If you received SMS messages, your SMS configuration is working correctly"
Write-Host "2. Check the staff service logs for detailed information"
Write-Host "3. Verify your SMS provider credentials in application.properties"
Write-Host ""
Write-Host "Configuration files to check:" -ForegroundColor Green
Write-Host "- staff-service/src/main/resources/application.properties"
Write-Host "- sms-config.yml"
Write-Host ""
Write-Host "Your test phone number:" -ForegroundColor Green
Write-Host $TestPhone
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "1. Update SMS provider credentials with real values"
Write-Host "2. Test with different phone numbers"
Write-Host "3. Monitor SMS delivery and costs"
Write-Host "4. Implement SMS functionality in other services as needed"
Write-Host ""
Write-Host "SMS Configuration Test Completed!" -ForegroundColor Green