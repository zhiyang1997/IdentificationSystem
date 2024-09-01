package com.engineer.recognitionSystem.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpService {

    // Twilio Account SID 和 Auth Token 
    private final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private final String FROM_PHONE_NUMBER = System.getenv("TWILIO_FROM_PHONE_NUMBER");

    public OtpService() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    // 生成 6 位数的 OTP
    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // 发送 OTP 到指定手機號碼
    public void sendOtp(String toPhoneNumber, String otp) {
        // 将用户输入的号码转换为国际格式
        if (toPhoneNumber.startsWith("0")) {
            toPhoneNumber = "+886" + toPhoneNumber.substring(1);
        }

        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(FROM_PHONE_NUMBER),
                "[此為測試簡訊]你的 OTP code 為，請於15分鐘內輸入: " + otp).create();
    }
}
