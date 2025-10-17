package com.medsync_hospital.staff_service.service;

import java.util.Map;

public interface SmsService {
    
    /**
     * Send SMS with message content
     * @param phoneNumber The recipient's phone number
     * @param message The message content
     * @return true if SMS sent successfully, false otherwise
     */
    boolean sendSms(String phoneNumber, String message);
    
    /**
     * Send OTP SMS
     * @param phoneNumber The recipient's phone number
     * @param otp The OTP code
     * @return true if OTP SMS sent successfully, false otherwise
     */
    boolean sendOtp(String phoneNumber, String otp);
    
    /**
     * Send templated SMS
     * @param phoneNumber The recipient's phone number
     * @param templateName The template name
     * @param variables The template variables
     * @return true if SMS sent successfully, false otherwise
     */
    boolean sendTemplatedSms(String phoneNumber, String templateName, Map<String, String> variables);
    
    /**
     * Generate OTP
     * @return Generated OTP string
     */
    String generateOtp();
    
    /**
     * Validate phone number format
     * @param phoneNumber The phone number to validate
     * @return true if valid, false otherwise
     */
    boolean isValidPhoneNumber(String phoneNumber);
}