# SMS Configuration Guide for MedSync Hospital System

## Overview

This guide explains how to configure SMS providers (Twilio and Fast2SMS) for the MedSync Hospital System to enable OTP functionality and SMS notifications.

## Supported SMS Providers

### 1. Twilio (International)
- **Best for**: Global SMS delivery
- **Supports**: International phone numbers
- **Reliability**: High
- **Cost**: Pay-per-SMS model

### 2. Fast2SMS (India)
- **Best for**: Indian phone numbers
- **Supports**: Indian mobile numbers
- **Reliability**: Good for Indian market
- **Cost**: Competitive rates for India

## Configuration Files

### 1. Main SMS Configuration (`sms-config.yml`)
```yaml
sms:
  provider: twilio  # or fast2sms
  twilio:
    account-sid: your-twilio-account-sid
    auth-token: your-twilio-auth-token
    from-number: +1234567890
  fast2sms:
    api-key: your-fast2sms-api-key
    sender-id: TRADES
```

### 2. Application Properties (`application.properties`)
```properties
# SMS Configuration
sms.provider=twilio
sms.twilio.account-sid=your-twilio-account-sid
sms.twilio.auth-token=your-twilio-auth-token
sms.twilio.from-number=+1234567890
sms.fast2sms.api-key=your-fast2sms-api-key
sms.fast2sms.sender-id=TRADES
```

## Setup Instructions

### Step 1: Choose Your SMS Provider

#### For Twilio:
1. Sign up at [https://www.twilio.com](https://www.twilio.com)
2. Get your Account SID and Auth Token from the Twilio Console
3. Purchase a phone number or use Twilio's messaging service
4. Update the configuration with your credentials

#### For Fast2SMS:
1. Sign up at [https://www.fast2sms.com](https://www.fast2sms.com)
2. Get your API Key from the dashboard
3. Choose a Sender ID (e.g., "TRADES")
4. Update the configuration with your credentials

### Step 2: Update Configuration

#### Option 1: Using application.properties
```properties
# For Twilio
sms.provider=twilio
sms.twilio.account-sid=ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
sms.twilio.auth-token=your_auth_token_here
sms.twilio.from-number=+1234567890

# For Fast2SMS
sms.provider=fast2sms
sms.fast2sms.api-key=your_api_key_here
sms.fast2sms.sender-id=TRADES
```

#### Option 2: Using Environment Variables
```bash
export SMS_PROVIDER=twilio
export SMS_TWILIO_ACCOUNT_SID=ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
export SMS_TWILIO_AUTH_TOKEN=your_auth_token_here
export SMS_TWILIO_FROM_NUMBER=+1234567890

# Or for Fast2SMS
export SMS_PROVIDER=fast2sms
export SMS_FAST2SMS_API_KEY=your_api_key_here
export SMS_FAST2SMS_SENDER_ID=TRADES
```

### Step 3: Test Configuration

Use the provided test endpoints to verify your SMS configuration:

```bash
# Send OTP
curl -X POST http://localhost:8081/api/sms/send-otp \
  -H "Content-Type: application/json" \
  -d '{"phoneNumber": "+919100784590"}'

# Verify OTP
curl -X POST http://localhost:8081/api/sms/verify-otp \
  -H "Content-Type: application/json" \
  -d '{"phoneNumber": "+919100784590", "otp": "123456"}'
```

## API Endpoints

### 1. Send OTP
- **URL**: `POST /api/sms/send-otp`
- **Body**: `{"phoneNumber": "+919100784590"}`
- **Response**: `{"success": true, "message": "OTP sent successfully"}`

### 2. Verify OTP
- **URL**: `POST /api/sms/verify-otp`
- **Body**: `{"phoneNumber": "+919100784590", "otp": "123456"}`
- **Response**: `{"success": true, "message": "OTP verified successfully"}`

### 3. Resend OTP
- **URL**: `POST /api/sms/resend-otp`
- **Body**: `{"phoneNumber": "+919100784590"}`
- **Response**: `{"success": true, "message": "OTP resent successfully"}`

### 4. Send Custom SMS
- **URL**: `POST /api/sms/send`
- **Body**: `{"phoneNumber": "+919100784590", "message": "Your custom message"}`
- **Response**: `{"success": true, "message": "SMS sent successfully"}`

## SMS Templates

The system supports predefined SMS templates:

### 1. OTP Template
```
Your OTP for MedSync Hospital is: {otp}. Valid for 10 minutes. Do not share this with anyone.
```

### 2. Appointment Reminder Template
```
Dear {patientName}, your appointment with Dr. {doctorName} is scheduled for {appointmentDate} at {appointmentTime}. Please arrive 15 minutes early.
```

### 3. Claim Status Template
```
Your insurance claim #{claimNumber} status has been updated to {status}. For details, contact us at {hospitalPhone}.
```

### 4. Registration Confirmation Template
```
Welcome to MedSync Hospital! Your registration is successful. Patient ID: {patientId}. Contact: {hospitalPhone}
```

## Configuration Parameters

### OTP Settings
```properties
sms.otp.length=6                    # OTP length (default: 6)
sms.otp.expiry-minutes=10          # OTP expiry time (default: 10 minutes)
sms.otp.max-attempts=3             # Max verification attempts (default: 3)
sms.otp.resend-interval-minutes=2  # Resend interval (default: 2 minutes)
```

### Rate Limiting
```properties
sms.rate-limit.max-sms-per-hour=10  # Max SMS per hour per number
sms.rate-limit.max-sms-per-day=50   # Max SMS per day per number
```

### Retry Configuration
```properties
sms.retry.max-attempts=3            # Max retry attempts
sms.retry.initial-delay-seconds=1   # Initial retry delay
sms.retry.max-delay-seconds=30      # Maximum retry delay
```

### Test Configuration
```properties
sms.test.enabled=false              # Enable test mode
sms.test.mock-response=true         # Mock SMS responses in test mode
sms.test.test-phone-numbers[0]=+919100784590  # Test phone numbers
```

## Security Best Practices

1. **Never commit credentials**: Use environment variables or external configuration
2. **Use HTTPS**: Always use HTTPS for API calls
3. **Rate limiting**: Implement proper rate limiting to prevent abuse
4. **Input validation**: Validate phone numbers and messages
5. **Logging**: Log SMS activities but never log sensitive information

## Troubleshooting

### Common Issues

#### 1. SMS Not Sending
- Check your provider credentials
- Verify phone number format
- Check account balance (for paid services)
- Review API rate limits

#### 2. Invalid Phone Number
- Ensure phone number includes country code
- Use E.164 format (+919100784590)
- Remove spaces and special characters

#### 3. OTP Not Working
- Check OTP expiry settings
- Verify attempt limits
- Ensure proper OTP generation

### Debug Mode
Enable debug logging to troubleshoot issues:
```properties
logging.level.com.medsync_hospital.staff_service.service=DEBUG
```

## Production Deployment

### Environment Variables
```bash
# Twilio Configuration
SMS_PROVIDER=twilio
SMS_TWILIO_ACCOUNT_SID=your_production_account_sid
SMS_TWILIO_AUTH_TOKEN=your_production_auth_token
SMS_TWILIO_FROM_NUMBER=your_production_phone_number

# Fast2SMS Configuration
SMS_PROVIDER=fast2sms
SMS_FAST2SMS_API_KEY=your_production_api_key
SMS_FAST2SMS_SENDER_ID=your_approved_sender_id
```

### Docker Configuration
```yaml
# docker-compose.yml
environment:
  - SMS_PROVIDER=twilio
  - SMS_TWILIO_ACCOUNT_SID=${TWILIO_ACCOUNT_SID}
  - SMS_TWILIO_AUTH_TOKEN=${TWILIO_AUTH_TOKEN}
  - SMS_TWILIO_FROM_NUMBER=${TWILIO_FROM_NUMBER}
```

## Cost Optimization

### Twilio
- Use Twilio's messaging services for better rates
- Implement proper rate limiting
- Monitor usage through Twilio Console

### Fast2SMS
- Choose appropriate route (promotional vs transactional)
- Monitor API usage
- Use bulk SMS for notifications

## Support

For SMS configuration support:
1. Check the logs for error messages
2. Verify provider account status
3. Test with the provided endpoints
4. Contact the development team for assistance

## Phone Number for Testing
- **Your Mobile**: +919100784590 (configured for testing)
- **Format**: Always use international format with country code
- **Provider**: Works with both Twilio and Fast2SMS

---

**Note**: Replace placeholder credentials with your actual provider credentials before deployment.