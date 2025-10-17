#!/bin/bash

# SMS Configuration Test Script for MedSync Hospital System
# This script tests the SMS functionality with your configured providers

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuration
BASE_URL="http://localhost:8081"
TEST_PHONE="+919100784590"  # Your mobile number for testing

echo -e "${YELLOW}=== MedSync Hospital SMS Configuration Test ===${NC}"
echo ""

# Function to test API endpoint
test_endpoint() {
    local endpoint=$1
    local data=$2
    local description=$3
    
    echo -e "${YELLOW}Testing: $description${NC}"
    echo "Endpoint: $endpoint"
    echo "Data: $data"
    echo ""
    
    response=$(curl -s -w "HTTPSTATUS:%{http_code}" \
        -X POST \
        -H "Content-Type: application/json" \
        -d "$data" \
        "$BASE_URL$endpoint")
    
    http_code=$(echo $response | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')
    body=$(echo $response | sed -e 's/HTTPSTATUS\:.*//g')
    
    if [ "$http_code" -eq 200 ]; then
        echo -e "${GREEN}✓ Success (HTTP $http_code)${NC}"
        echo "Response: $body"
    else
        echo -e "${RED}✗ Failed (HTTP $http_code)${NC}"
        echo "Response: $body"
    fi
    
    echo ""
    echo "---"
    echo ""
}

# Check if staff service is running
echo -e "${YELLOW}Checking if Staff Service is running...${NC}"
health_check=$(curl -s -w "HTTPSTATUS:%{http_code}" "$BASE_URL/actuator/health" 2>/dev/null)
health_code=$(echo $health_check | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')

if [ "$health_code" -eq 200 ]; then
    echo -e "${GREEN}✓ Staff Service is running${NC}"
else
    echo -e "${RED}✗ Staff Service is not running or not accessible${NC}"
    echo "Please start the staff service first:"
    echo "cd staff-service && mvn spring-boot:run"
    exit 1
fi

echo ""
echo "---"
echo ""

# Test 1: Send OTP
test_endpoint "/api/sms/send-otp" \
    "{\"phoneNumber\": \"$TEST_PHONE\"}" \
    "Send OTP to $TEST_PHONE"

# Wait a moment before next test
sleep 2

# Test 2: Send Custom SMS
test_endpoint "/api/sms/send" \
    "{\"phoneNumber\": \"$TEST_PHONE\", \"message\": \"Test SMS from MedSync Hospital System\"}" \
    "Send Custom SMS to $TEST_PHONE"

# Wait a moment before next test
sleep 2

# Test 3: Resend OTP (should be rate limited)
test_endpoint "/api/sms/resend-otp" \
    "{\"phoneNumber\": \"$TEST_PHONE\"}" \
    "Resend OTP (should be rate limited)"

# Test 4: Verify OTP (with dummy OTP)
echo -e "${YELLOW}Testing OTP Verification${NC}"
echo "Please enter the OTP you received (or press Enter to skip): "
read -r user_otp

if [ -n "$user_otp" ]; then
    test_endpoint "/api/sms/verify-otp" \
        "{\"phoneNumber\": \"$TEST_PHONE\", \"otp\": \"$user_otp\"}" \
        "Verify OTP: $user_otp"
else
    echo -e "${YELLOW}Skipping OTP verification test${NC}"
    echo ""
fi

# Test 5: Invalid phone number
test_endpoint "/api/sms/send-otp" \
    "{\"phoneNumber\": \"invalid-phone\"}" \
    "Send OTP to invalid phone number (should fail)"

# Test 6: Empty message
test_endpoint "/api/sms/send" \
    "{\"phoneNumber\": \"$TEST_PHONE\", \"message\": \"\"}" \
    "Send empty message (should fail)"

echo -e "${YELLOW}=== Test Summary ===${NC}"
echo ""
echo "1. If you received SMS messages, your SMS configuration is working correctly"
echo "2. Check the staff service logs for detailed information"
echo "3. Verify your SMS provider credentials in application.properties"
echo ""
echo -e "${GREEN}Configuration files to check:${NC}"
echo "- staff-service/src/main/resources/application.properties"
echo "- sms-config.yml"
echo ""
echo -e "${GREEN}Your test phone number:${NC} $TEST_PHONE"
echo ""
echo -e "${YELLOW}Next steps:${NC}"
echo "1. Update SMS provider credentials with real values"
echo "2. Test with different phone numbers"
echo "3. Monitor SMS delivery and costs"
echo "4. Implement SMS functionality in other services as needed"
echo ""
echo -e "${GREEN}SMS Configuration Test Completed!${NC}"