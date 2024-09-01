package com.engineer.recognitionSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.engineer.recognitionSystem.service.OtpService;

@RestController
@RequestMapping("/api/otp")
public class OtpController {
    
    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public String sendOtp(@RequestParam String phoneNumber) {

         // 生成 OTP
         String otp = otpService.generateOtp();
         // 发送 OTP 到用户手机
         otpService.sendOtp(phoneNumber, otp);
         return "已發送簡訊到您的手機。";
    }
}
