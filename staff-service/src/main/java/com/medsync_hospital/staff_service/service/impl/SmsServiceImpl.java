package com.medsync_hospital.staff_service.service.impl;

import com.medsync_hospital.staff_service.config.SmsConfig;
import com.medsync_hospital.staff_service.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class SmsServiceImpl implements SmsService {
    
    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$");
    private static final SecureRandom random = new SecureRandom();
    
    @Autowired
    private SmsConfig smsConfig;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public boolean sendSms(String phoneNumber, String message) {
        if (!isValidPhoneNumber(phoneNumber)) {
            logger.error("Invalid phone number: {}", phoneNumber);
            return false;
        }
        
        if (smsConfig.getTest().isEnabled() && smsConfig.getTest().isMockResponse()) {
            logger.info("Mock SMS sent to {} with message: {}", phoneNumber, message);
            return true;
        }
        
        try {
            switch (smsConfig.getProvider().toLowerCase()) {
                case "twilio":
                    return sendViaTwilio(phoneNumber, message);
                case "fast2sms":
                    return sendViaFast2Sms(phoneNumber, message);
                default:
                    logger.error("Unknown SMS provider: {}", smsConfig.getProvider());
                    return false;
            }
        } catch (Exception e) {
            logger.error("Failed to send SMS to {}: {}", phoneNumber, e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean sendOtp(String phoneNumber, String otp) {
        String template = smsConfig.getTemplates().get("otp");
        if (template == null) {
            template = "Your OTP for MedSync Hospital is: {otp}. Valid for 10 minutes. Do not share this with anyone.";
        }
        
        String message = template.replace("{otp}", otp);
        return sendSms(phoneNumber, message);
    }
    
    @Override
    public boolean sendTemplatedSms(String phoneNumber, String templateName, Map<String, String> variables) {
        String template = smsConfig.getTemplates().get(templateName);
        if (template == null) {
            logger.error("Template not found: {}", templateName);
            return false;
        }
        
        String message = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            message = message.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        
        return sendSms(phoneNumber, message);
    }
    
    @Override
    public String generateOtp() {
        int length = smsConfig.getOtp().getLength();
        StringBuilder otp = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        
        return otp.toString();
    }
    
    @Override
    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }
    
    private boolean sendViaTwilio(String phoneNumber, String message) {
        logger.info("Sending SMS via Twilio to: {}", phoneNumber);
        
        // Twilio API implementation
        String accountSid = smsConfig.getTwilio().getAccountSid();
        String authToken = smsConfig.getTwilio().getAuthToken();
        String fromNumber = smsConfig.getTwilio().getFromNumber();
        
        if (accountSid == null || authToken == null || fromNumber == null) {
            logger.error("Twilio configuration is incomplete");
            return false;
        }
        
        try {
            // Create Twilio API request
            String url = String.format("https://api.twilio.com/2010-04-01/Accounts/%s/Messages.json", accountSid);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(accountSid, authToken);
            
            String body = String.format("To=%s&From=%s&Body=%s", phoneNumber, fromNumber, message);
            
            HttpEntity<String> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            
            if (response.getStatusCode() == HttpStatus.CREATED) {
                logger.info("SMS sent successfully via Twilio to: {}", phoneNumber);
                return true;
            } else {
                logger.error("Failed to send SMS via Twilio. Status: {}, Response: {}", 
                    response.getStatusCode(), response.getBody());
                return false;
            }
            
        } catch (Exception e) {
            logger.error("Error sending SMS via Twilio: {}", e.getMessage());
            return false;
        }
    }
    
    private boolean sendViaFast2Sms(String phoneNumber, String message) {
        logger.info("Sending SMS via Fast2SMS to: {}", phoneNumber);
        
        String apiKey = smsConfig.getFast2sms().getApiKey();
        String senderId = smsConfig.getFast2sms().getSenderId();
        String apiUrl = smsConfig.getFast2sms().getApiUrl();
        String route = smsConfig.getFast2sms().getRoute();
        
        if (apiKey == null || senderId == null) {
            logger.error("Fast2SMS configuration is incomplete");
            return false;
        }
        
        try {
            // Remove country code for Indian numbers if present
            String cleanPhoneNumber = phoneNumber.startsWith("+91") ? 
                phoneNumber.substring(3) : phoneNumber.replaceAll("^\\+", "");
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("authorization", apiKey);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("route", route);
            requestBody.put("sender_id", senderId);
            requestBody.put("message", message);
            requestBody.put("language", "english");
            requestBody.put("flash", 0);
            requestBody.put("numbers", cleanPhoneNumber);
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("SMS sent successfully via Fast2SMS to: {}", phoneNumber);
                return true;
            } else {
                logger.error("Failed to send SMS via Fast2SMS. Status: {}, Response: {}", 
                    response.getStatusCode(), response.getBody());
                return false;
            }
            
        } catch (Exception e) {
            logger.error("Error sending SMS via Fast2SMS: {}", e.getMessage());
            return false;
        }
    }
}