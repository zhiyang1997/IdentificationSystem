package com.engineer.recognitionSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Step2ReqDto {

    @JsonProperty("OTP_DIGITS")
    private String otpDigits;
}
