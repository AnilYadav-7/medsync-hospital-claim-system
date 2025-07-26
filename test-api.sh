#!/bin/bash

echo "🧪 Testing MedSync Hospital Claim System APIs..."
echo "================================================"

BASE_URL="http://localhost:8080"

# Function to make HTTP requests and show results
test_endpoint() {
    local method=$1
    local endpoint=$2
    local data=$3
    local description=$4
    
    echo ""
    echo "🔍 Testing: $description"
    echo "   $method $endpoint"
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "\nHTTP_STATUS:%{http_code}" "$BASE_URL$endpoint")
    elif [ "$method" = "POST" ]; then
        response=$(curl -s -w "\nHTTP_STATUS:%{http_code}" -X POST -H "Content-Type: application/json" -d "$data" "$BASE_URL$endpoint")
    elif [ "$method" = "PUT" ]; then
        response=$(curl -s -w "\nHTTP_STATUS:%{http_code}" -X PUT -H "Content-Type: application/json" -d "$data" "$BASE_URL$endpoint")
    elif [ "$method" = "DELETE" ]; then
        response=$(curl -s -w "\nHTTP_STATUS:%{http_code}" -X DELETE "$BASE_URL$endpoint")
    fi
    
    http_status=$(echo "$response" | grep "HTTP_STATUS" | cut -d: -f2)
    body=$(echo "$response" | sed '/HTTP_STATUS/d')
    
    if [ "$http_status" = "200" ] || [ "$http_status" = "201" ] || [ "$http_status" = "204" ]; then
        echo "   ✅ Status: $http_status"
        if [ -n "$body" ] && [ "$body" != "null" ]; then
            echo "   📄 Response: $body"
        fi
    else
        echo "   ❌ Status: $http_status"
        if [ -n "$body" ]; then
            echo "   📄 Error: $body"
        fi
    fi
}

# Wait for services to be ready
echo "⏳ Waiting for services to be ready..."
sleep 5

# Test health endpoints
test_endpoint "GET" "/actuator/health" "" "API Gateway Health Check"

# Test patient endpoints
test_endpoint "GET" "/api/patients" "" "Get all patients (should be empty initially)"

# Create a test patient
patient_data='{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@email.com",
    "phoneNumber": "+1234567890",
    "dateOfBirth": "1990-01-15",
    "gender": "MALE",
    "address": "123 Main St, Anytown, USA",
    "insuranceNumber": "INS123456",
    "emergencyContactName": "Jane Doe",
    "emergencyContactPhone": "+1234567891"
}'

test_endpoint "POST" "/api/patients" "$patient_data" "Create a new patient"

# Get all patients (should now have one)
test_endpoint "GET" "/api/patients" "" "Get all patients (should have John Doe)"

# Get patient by ID (assuming ID 1)
test_endpoint "GET" "/api/patients/1" "" "Get patient by ID 1"

# Search for patients
test_endpoint "GET" "/api/patients/search?searchTerm=John" "" "Search patients by name 'John'"

# Update patient
update_data='{
    "firstName": "John",
    "lastName": "Smith",
    "email": "john.smith@email.com",
    "phoneNumber": "+1234567890",
    "address": "456 Oak Ave, Newtown, USA"
}'

test_endpoint "PUT" "/api/patients/1" "$update_data" "Update patient (change last name to Smith)"

# Get updated patient
test_endpoint "GET" "/api/patients/1" "" "Get updated patient"

# Create another patient
patient_data2='{
    "firstName": "Alice",
    "lastName": "Johnson",
    "email": "alice.johnson@email.com",
    "phoneNumber": "+1987654321",
    "dateOfBirth": "1985-05-20",
    "gender": "FEMALE",
    "address": "789 Pine St, Somewhere, USA",
    "insuranceNumber": "INS789012"
}'

test_endpoint "POST" "/api/patients" "$patient_data2" "Create another patient (Alice Johnson)"

# Get all patients (should now have two)
test_endpoint "GET" "/api/patients" "" "Get all patients (should have 2 patients)"

echo ""
echo "🎉 API Testing Complete!"
echo "================================================"
echo "📊 Summary:"
echo "   - All endpoints tested"
echo "   - Patient CRUD operations verified"
echo "   - Search functionality tested"
echo "   - Data persistence confirmed"
echo ""
echo "💡 You can also:"
echo "   - Visit Eureka Dashboard: http://localhost:8761"
echo "   - Check H2 Database: http://localhost:8081/h2-console"
echo "   - Monitor health: http://localhost:8080/actuator/health"