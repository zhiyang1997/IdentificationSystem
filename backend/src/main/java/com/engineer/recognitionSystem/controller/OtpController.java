package com.engineer.recognitionSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineer.recognitionSystem.dto.FromDataReqDto;
import com.engineer.recognitionSystem.service.OtpService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/sendotp")
    public ResponseEntity<String> sendOtp(@RequestBody FromDataReqDto reqDto) {

        System.out.println("reqDto: " + reqDto);
        String phoneNumber = reqDto.getStep1ReqDto().getPhoneNumber();
        System.out.println("phoneNumber: " + phoneNumber);
        // 生成 OTP
        String otp = otpService.generateOtp();
        // 发送 OTP 到用户手机
        otpService.sendOtp(phoneNumber, otp);
        return ResponseEntity.ok("已發送簡訊到您的手機，請於1分鐘內輸入。");
    }
}
