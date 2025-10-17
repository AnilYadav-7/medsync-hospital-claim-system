package com.medsync_hospital.staff_service.service.impl;

import com.medsync_hospital.staff_service.config.SmsConfig;
import com.medsync_hospital.staff_service.service.OtpService;
import com.medsync_hospital.staff_service.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpServiceImpl implements OtpService {
    
    private static final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);
    
    // In-memory storage for OTP data (in production, use Redis or database)
    private final Map<String, OtpData> otpStorage = new ConcurrentHashMap<>();
    
    @Autowired
    private SmsService smsService;
    
    @Autowired
    private SmsConfig smsConfig;
    
    @Override
    public boolean generateAndSendOtp(String phoneNumber) {
        if (!smsService.isValidPhoneNumber(phoneNumber)) {
            logger.error("Invalid phone number: {}", phoneNumber);
            return false;
        }
        
        // Check rate limiting
        if (!canResendOtp(phoneNumber)) {
            logger.warn("Rate limit exceeded for phone number: {}", phoneNumber);
            return false;
        }
        
        // Generate OTP
        String otp = smsService.generateOtp();
        
        // Store OTP data
        OtpData otpData = new OtpData(otp, LocalDateTime.now(), 0);
        otpStorage.put(phoneNumber, otpData);
        
        // Send OTP via SMS
        boolean sent = smsService.sendOtp(phoneNumber, otp);
        
        if (sent) {
            logger.info("OTP generated and sent successfully to: {}", phoneNumber);
        } else {
            logger.error("Failed to send OTP to: {}", phoneNumber);
            otpStorage.remove(phoneNumber); // Remove if sending failed
        }
        
        return sent;
    }
    
    @Override
    public boolean verifyOtp(String phoneNumber, String otp) {
        OtpData otpData = otpStorage.get(phoneNumber);
        
        if (otpData == null) {
            logger.warn("No OTP found for phone number: {}", phoneNumber);
            return false;
        }
        
        // Check if OTP is expired
        LocalDateTime expiryTime = otpData.getGeneratedAt()
            .plusMinutes(smsConfig.getOtp().getExpiryMinutes());
        
        if (LocalDateTime.now().isAfter(expiryTime)) {
            logger.warn("OTP expired for phone number: {}", phoneNumber);
            otpStorage.remove(phoneNumber);
            return false;
        }
        
        // Check if max attempts exceeded
        if (otpData.getAttempts() >= smsConfig.getOtp().getMaxAttempts()) {
            logger.warn("Max OTP verification attempts exceeded for phone number: {}", phoneNumber);
            otpStorage.remove(phoneNumber);
            return false;
        }
        
        // Increment attempt count
        otpData.incrementAttempts();
        
        // Verify OTP
        if (otpData.getOtp().equals(otp)) {
            logger.info("OTP verified successfully for phone number: {}", phoneNumber);
            otpStorage.remove(phoneNumber); // Remove after successful verification
            return true;
        } else {
            logger.warn("Invalid OTP provided for phone number: {}", phoneNumber);
            return false;
        }
    }
    
    @Override
    public boolean resendOtp(String phoneNumber) {
        return generateAndSendOtp(phoneNumber);
    }
    
    @Override
    public void clearOtp(String phoneNumber) {
        otpStorage.remove(phoneNumber);
        logger.info("OTP cleared for phone number: {}", phoneNumber);
    }
    
    @Override
    public boolean canResendOtp(String phoneNumber) {
        OtpData otpData = otpStorage.get(phoneNumber);
        
        if (otpData == null) {
            return true; // No previous OTP, can send
        }
        
        // Check if enough time has passed since last OTP
        LocalDateTime nextAllowedTime = otpData.getGeneratedAt()
            .plusMinutes(smsConfig.getOtp().getResendIntervalMinutes());
        
        return LocalDateTime.now().isAfter(nextAllowedTime);
    }
    
    // Inner class to store OTP data
    private static class OtpData {
        private final String otp;
        private final LocalDateTime generatedAt;
        private int attempts;
        
        public OtpData(String otp, LocalDateTime generatedAt, int attempts) {
            this.otp = otp;
            this.generatedAt = generatedAt;
            this.attempts = attempts;
        }
        
        public String getOtp() { return otp; }
        public LocalDateTime getGeneratedAt() { return generatedAt; }
        public int getAttempts() { return attempts; }
        public void incrementAttempts() { this.attempts++; }
    }
}