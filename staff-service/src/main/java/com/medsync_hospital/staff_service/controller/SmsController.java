package com.medsync_hospital.staff_service.controller;

import com.medsync_hospital.staff_service.service.OtpService;
import com.medsync_hospital.staff_service.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sms")
@CrossOrigin(origins = "*")
public class SmsController {
    
    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);
    
    @Autowired
    private SmsService smsService;
    
    @Autowired
    private OtpService otpService;
    
    /**
     * Send OTP to phone number
     */
    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String phoneNumber = request.get("phoneNumber");
            
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Phone number is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean sent = otpService.generateAndSendOtp(phoneNumber);
            
            if (sent) {
                response.put("success", true);
                response.put("message", "OTP sent successfully");
                logger.info("OTP sent successfully to: {}", phoneNumber);
            } else {
                response.put("success", false);
                response.put("message", "Failed to send OTP");
                logger.error("Failed to send OTP to: {}", phoneNumber);
            }
            
        } catch (Exception e) {
            logger.error("Error sending OTP: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Internal server error");
            return ResponseEntity.internalServerError().body(response);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Verify OTP
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String phoneNumber = request.get("phoneNumber");
            String otp = request.get("otp");
            
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Phone number is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (otp == null || otp.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "OTP is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean verified = otpService.verifyOtp(phoneNumber, otp);
            
            if (verified) {
                response.put("success", true);
                response.put("message", "OTP verified successfully");
                logger.info("OTP verified successfully for: {}", phoneNumber);
            } else {
                response.put("success", false);
                response.put("message", "Invalid or expired OTP");
                logger.warn("OTP verification failed for: {}", phoneNumber);
            }
            
        } catch (Exception e) {
            logger.error("Error verifying OTP: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Internal server error");
            return ResponseEntity.internalServerError().body(response);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Resend OTP
     */
    @PostMapping("/resend-otp")
    public ResponseEntity<Map<String, Object>> resendOtp(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String phoneNumber = request.get("phoneNumber");
            
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Phone number is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (!otpService.canResendOtp(phoneNumber)) {
                response.put("success", false);
                response.put("message", "Please wait before requesting another OTP");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean sent = otpService.resendOtp(phoneNumber);
            
            if (sent) {
                response.put("success", true);
                response.put("message", "OTP resent successfully");
                logger.info("OTP resent successfully to: {}", phoneNumber);
            } else {
                response.put("success", false);
                response.put("message", "Failed to resend OTP");
                logger.error("Failed to resend OTP to: {}", phoneNumber);
            }
            
        } catch (Exception e) {
            logger.error("Error resending OTP: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Internal server error");
            return ResponseEntity.internalServerError().body(response);
        }
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Send custom SMS
     */
    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendSms(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String phoneNumber = request.get("phoneNumber");
            String message = request.get("message");
            
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Phone number is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (message == null || message.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Message is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean sent = smsService.sendSms(phoneNumber, message);
            
            if (sent) {
                response.put("success", true);
                response.put("message", "SMS sent successfully");
                logger.info("SMS sent successfully to: {}", phoneNumber);
            } else {
                response.put("success", false);
                response.put("message", "Failed to send SMS");
                logger.error("Failed to send SMS to: {}", phoneNumber);
            }
            
        } catch (Exception e) {
            logger.error("Error sending SMS: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Internal server error");
            return ResponseEntity.internalServerError().body(response);
        }
        
        return ResponseEntity.ok(response);
    }
}