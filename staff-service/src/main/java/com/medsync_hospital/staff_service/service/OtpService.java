package com.medsync_hospital.staff_service.service;

public interface OtpService {
    
    /**
     * Generate and send OTP to phone number
     * @param phoneNumber The recipient's phone number
     * @return true if OTP sent successfully, false otherwise
     */
    boolean generateAndSendOtp(String phoneNumber);
    
    /**
     * Verify OTP
     * @param phoneNumber The phone number
     * @param otp The OTP to verify
     * @return true if OTP is valid, false otherwise
     */
    boolean verifyOtp(String phoneNumber, String otp);
    
    /**
     * Resend OTP
     * @param phoneNumber The recipient's phone number
     * @return true if OTP resent successfully, false otherwise
     */
    boolean resendOtp(String phoneNumber);
    
    /**
     * Clear OTP for phone number
     * @param phoneNumber The phone number
     */
    void clearOtp(String phoneNumber);
    
    /**
     * Check if OTP can be resent (based on rate limiting)
     * @param phoneNumber The phone number
     * @return true if OTP can be resent, false otherwise
     */
    boolean canResendOtp(String phoneNumber);
}