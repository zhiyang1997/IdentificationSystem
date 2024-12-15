package com.engineer.recognitionSystem.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpService {

    // Twilio Account SID 和 Auth Token
    public static final String ACCOUNT_SID = "AC90e8861c32f4cd69396f18915cbca106";
    public static final String AUTH_TOKEN = "53510363097821af83107e4138f8a461";
    private final String FROM_PHONE_NUMBER = "+14439918839";

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
                "你的 OTP驗證碼為，請於1分鐘內輸入: " + otp).create();
    }
}
