package com.medsync_hospital.staff_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {
    
    private String provider = "twilio";
    private TwilioConfig twilio = new TwilioConfig();
    private Fast2SmsConfig fast2sms = new Fast2SmsConfig();
    private Map<String, String> templates;
    private OtpConfig otp = new OtpConfig();
    private RateLimitConfig rateLimit = new RateLimitConfig();
    private RetryConfig retry = new RetryConfig();
    private TestConfig test = new TestConfig();
    
    // Getters and Setters
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    
    public TwilioConfig getTwilio() { return twilio; }
    public void setTwilio(TwilioConfig twilio) { this.twilio = twilio; }
    
    public Fast2SmsConfig getFast2sms() { return fast2sms; }
    public void setFast2sms(Fast2SmsConfig fast2sms) { this.fast2sms = fast2sms; }
    
    public Map<String, String> getTemplates() { return templates; }
    public void setTemplates(Map<String, String> templates) { this.templates = templates; }
    
    public OtpConfig getOtp() { return otp; }
    public void setOtp(OtpConfig otp) { this.otp = otp; }
    
    public RateLimitConfig getRateLimit() { return rateLimit; }
    public void setRateLimit(RateLimitConfig rateLimit) { this.rateLimit = rateLimit; }
    
    public RetryConfig getRetry() { return retry; }
    public void setRetry(RetryConfig retry) { this.retry = retry; }
    
    public TestConfig getTest() { return test; }
    public void setTest(TestConfig test) { this.test = test; }
    
    // Inner Classes
    public static class TwilioConfig {
        private String accountSid;
        private String authToken;
        private String fromNumber;
        private String serviceSid;
        private String webhookUrl;
        
        // Getters and Setters
        public String getAccountSid() { return accountSid; }
        public void setAccountSid(String accountSid) { this.accountSid = accountSid; }
        
        public String getAuthToken() { return authToken; }
        public void setAuthToken(String authToken) { this.authToken = authToken; }
        
        public String getFromNumber() { return fromNumber; }
        public void setFromNumber(String fromNumber) { this.fromNumber = fromNumber; }
        
        public String getServiceSid() { return serviceSid; }
        public void setServiceSid(String serviceSid) { this.serviceSid = serviceSid; }
        
        public String getWebhookUrl() { return webhookUrl; }
        public void setWebhookUrl(String webhookUrl) { this.webhookUrl = webhookUrl; }
    }
    
    public static class Fast2SmsConfig {
        private String apiKey;
        private String senderId;
        private String apiUrl = "https://www.fast2sms.com/dev/bulkV2";
        private String route = "t";
        
        // Getters and Setters
        public String getApiKey() { return apiKey; }
        public void setApiKey(String apiKey) { this.apiKey = apiKey; }
        
        public String getSenderId() { return senderId; }
        public void setSenderId(String senderId) { this.senderId = senderId; }
        
        public String getApiUrl() { return apiUrl; }
        public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }
        
        public String getRoute() { return route; }
        public void setRoute(String route) { this.route = route; }
    }
    
    public static class OtpConfig {
        private int length = 6;
        private int expiryMinutes = 10;
        private int maxAttempts = 3;
        private int resendIntervalMinutes = 2;
        
        // Getters and Setters
        public int getLength() { return length; }
        public void setLength(int length) { this.length = length; }
        
        public int getExpiryMinutes() { return expiryMinutes; }
        public void setExpiryMinutes(int expiryMinutes) { this.expiryMinutes = expiryMinutes; }
        
        public int getMaxAttempts() { return maxAttempts; }
        public void setMaxAttempts(int maxAttempts) { this.maxAttempts = maxAttempts; }
        
        public int getResendIntervalMinutes() { return resendIntervalMinutes; }
        public void setResendIntervalMinutes(int resendIntervalMinutes) { this.resendIntervalMinutes = resendIntervalMinutes; }
    }
    
    public static class RateLimitConfig {
        private int maxSmsPerHour = 10;
        private int maxSmsPerDay = 50;
        
        // Getters and Setters
        public int getMaxSmsPerHour() { return maxSmsPerHour; }
        public void setMaxSmsPerHour(int maxSmsPerHour) { this.maxSmsPerHour = maxSmsPerHour; }
        
        public int getMaxSmsPerDay() { return maxSmsPerDay; }
        public void setMaxSmsPerDay(int maxSmsPerDay) { this.maxSmsPerDay = maxSmsPerDay; }
    }
    
    public static class RetryConfig {
        private int maxAttempts = 3;
        private int initialDelaySeconds = 1;
        private int maxDelaySeconds = 30;
        
        // Getters and Setters
        public int getMaxAttempts() { return maxAttempts; }
        public void setMaxAttempts(int maxAttempts) { this.maxAttempts = maxAttempts; }
        
        public int getInitialDelaySeconds() { return initialDelaySeconds; }
        public void setInitialDelaySeconds(int initialDelaySeconds) { this.initialDelaySeconds = initialDelaySeconds; }
        
        public int getMaxDelaySeconds() { return maxDelaySeconds; }
        public void setMaxDelaySeconds(int maxDelaySeconds) { this.maxDelaySeconds = maxDelaySeconds; }
    }
    
    public static class TestConfig {
        private boolean enabled = false;
        private List<String> testPhoneNumbers;
        private boolean mockResponse = true;
        
        // Getters and Setters
        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        
        public List<String> getTestPhoneNumbers() { return testPhoneNumbers; }
        public void setTestPhoneNumbers(List<String> testPhoneNumbers) { this.testPhoneNumbers = testPhoneNumbers; }
        
        public boolean isMockResponse() { return mockResponse; }
        public void setMockResponse(boolean mockResponse) { this.mockResponse = mockResponse; }
    }
}