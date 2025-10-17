#!/bin/bash

# SMS Configuration Validation Script
# This script validates your SMS configuration before testing

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== SMS Configuration Validation ===${NC}"
echo ""

# Check if configuration files exist
CONFIG_FILES=(
    "sms-config.yml"
    "staff-service/src/main/resources/application.properties"
)

echo -e "${YELLOW}Checking configuration files...${NC}"
for file in "${CONFIG_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✓ Found: $file${NC}"
    else
        echo -e "${RED}✗ Missing: $file${NC}"
    fi
done

echo ""

# Check application.properties for SMS configuration
PROPS_FILE="staff-service/src/main/resources/application.properties"
if [ -f "$PROPS_FILE" ]; then
    echo -e "${YELLOW}Checking SMS configuration in application.properties...${NC}"
    
    # Check for SMS provider
    if grep -q "sms.provider=" "$PROPS_FILE"; then
        provider=$(grep "sms.provider=" "$PROPS_FILE" | cut -d'=' -f2)
        echo -e "${GREEN}✓ SMS Provider configured: $provider${NC}"
        
        # Check provider-specific configuration
        case "$provider" in
            "twilio")
                if grep -q "sms.twilio.account-sid=" "$PROPS_FILE" && 
                   grep -q "sms.twilio.auth-token=" "$PROPS_FILE" && 
                   grep -q "sms.twilio.from-number=" "$PROPS_FILE"; then
                    echo -e "${GREEN}✓ Twilio configuration found${NC}"
                    
                    # Check if using placeholder values
                    if grep -q "your-twilio-account-sid" "$PROPS_FILE"; then
                        echo -e "${YELLOW}⚠ Warning: Using placeholder Twilio Account SID${NC}"
                    fi
                    if grep -q "your-twilio-auth-token" "$PROPS_FILE"; then
                        echo -e "${YELLOW}⚠ Warning: Using placeholder Twilio Auth Token${NC}"
                    fi
                else
                    echo -e "${RED}✗ Incomplete Twilio configuration${NC}"
                fi
                ;;
            "fast2sms")
                if grep -q "sms.fast2sms.api-key=" "$PROPS_FILE" && 
                   grep -q "sms.fast2sms.sender-id=" "$PROPS_FILE"; then
                    echo -e "${GREEN}✓ Fast2SMS configuration found${NC}"
                    
                    # Check if using placeholder values
                    if grep -q "your-fast2sms-api-key" "$PROPS_FILE"; then
                        echo -e "${YELLOW}⚠ Warning: Using placeholder Fast2SMS API Key${NC}"
                    fi
                else
                    echo -e "${RED}✗ Incomplete Fast2SMS configuration${NC}"
                fi
                ;;
            *)
                echo -e "${RED}✗ Unknown SMS provider: $provider${NC}"
                ;;
        esac
    else
        echo -e "${RED}✗ SMS provider not configured${NC}"
    fi
    
    # Check for SMS templates
    if grep -q "sms.templates.otp=" "$PROPS_FILE"; then
        echo -e "${GREEN}✓ SMS templates configured${NC}"
    else
        echo -e "${YELLOW}⚠ SMS templates not found (will use defaults)${NC}"
    fi
    
    # Check for OTP configuration
    if grep -q "sms.otp.length=" "$PROPS_FILE"; then
        echo -e "${GREEN}✓ OTP configuration found${NC}"
    else
        echo -e "${YELLOW}⚠ OTP configuration not found (will use defaults)${NC}"
    fi
    
else
    echo -e "${RED}✗ application.properties not found${NC}"
fi

echo ""

# Check for Maven dependencies
POM_FILE="staff-service/pom.xml"
if [ -f "$POM_FILE" ]; then
    echo -e "${YELLOW}Checking Maven dependencies...${NC}"
    
    required_deps=(
        "spring-boot-starter-web"
        "spring-boot-starter-validation"
    )
    
    for dep in "${required_deps[@]}"; do
        if grep -q "$dep" "$POM_FILE"; then
            echo -e "${GREEN}✓ Found dependency: $dep${NC}"
        else
            echo -e "${RED}✗ Missing dependency: $dep${NC}"
        fi
    done
else
    echo -e "${RED}✗ pom.xml not found${NC}"
fi

echo ""

# Check Java source files
echo -e "${YELLOW}Checking SMS service implementation...${NC}"

JAVA_FILES=(
    "staff-service/src/main/java/com/medsync_hospital/staff_service/config/SmsConfig.java"
    "staff-service/src/main/java/com/medsync_hospital/staff_service/service/SmsService.java"
    "staff-service/src/main/java/com/medsync_hospital/staff_service/service/impl/SmsServiceImpl.java"
    "staff-service/src/main/java/com/medsync_hospital/staff_service/service/OtpService.java"
    "staff-service/src/main/java/com/medsync_hospital/staff_service/service/impl/OtpServiceImpl.java"
    "staff-service/src/main/java/com/medsync_hospital/staff_service/controller/SmsController.java"
    "staff-service/src/main/java/com/medsync_hospital/staff_service/config/RestTemplateConfig.java"
)

for file in "${JAVA_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✓ Found: $(basename "$file")${NC}"
    else
        echo -e "${RED}✗ Missing: $(basename "$file")${NC}"
    fi
done

echo ""

# Environment variables check
echo -e "${YELLOW}Checking environment variables...${NC}"

ENV_VARS=(
    "SMS_PROVIDER"
    "SMS_TWILIO_ACCOUNT_SID"
    "SMS_TWILIO_AUTH_TOKEN"
    "SMS_TWILIO_FROM_NUMBER"
    "SMS_FAST2SMS_API_KEY"
    "SMS_FAST2SMS_SENDER_ID"
)

env_found=false
for var in "${ENV_VARS[@]}"; do
    if [ -n "${!var}" ]; then
        echo -e "${GREEN}✓ Found environment variable: $var${NC}"
        env_found=true
    fi
done

if [ "$env_found" = false ]; then
    echo -e "${YELLOW}⚠ No SMS environment variables found (using configuration files)${NC}"
fi

echo ""

# Validation summary
echo -e "${BLUE}=== Validation Summary ===${NC}"
echo ""

if [ -f "$PROPS_FILE" ] && grep -q "sms.provider=" "$PROPS_FILE"; then
    provider=$(grep "sms.provider=" "$PROPS_FILE" | cut -d'=' -f2)
    
    echo -e "${GREEN}Configuration Status: Ready for testing${NC}"
    echo -e "SMS Provider: $provider"
    echo -e "Test Phone: +919100784590"
    echo ""
    echo -e "${YELLOW}Next Steps:${NC}"
    echo "1. Update placeholder credentials with real values"
    echo "2. Start the staff service: cd staff-service && mvn spring-boot:run"
    echo "3. Run the test script: ./test-sms-config.sh"
    echo ""
    
    # Check for placeholder values
    if grep -q "your-twilio-account-sid\|your-twilio-auth-token\|your-fast2sms-api-key" "$PROPS_FILE"; then
        echo -e "${YELLOW}⚠ Warning: Please replace placeholder values with real credentials${NC}"
        echo ""
    fi
    
else
    echo -e "${RED}Configuration Status: Incomplete${NC}"
    echo ""
    echo -e "${YELLOW}Required Actions:${NC}"
    echo "1. Configure SMS provider in application.properties"
    echo "2. Add SMS provider credentials"
    echo "3. Run this validation script again"
    echo ""
fi

echo -e "${GREEN}Validation completed!${NC}"