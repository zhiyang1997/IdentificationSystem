package com.engineer.recognitionSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Step4ReqDto {

    @JsonProperty("ID_CARD_FRONT")
    private String idCardFront;

    @JsonProperty("ID_CARD_BACK")
    private String idCardBack;

    @JsonProperty("HEALTH_INSURANCE_CARD")
    private String healthInsuranceCard;

    @JsonProperty("SELFIE_WITH_ID_CARD")
    private String selfieWithIdCard;

    @JsonProperty("FINANCIAL_PROOF")
    private String financialProof;

    @JsonProperty("OTHER_DOCUMENT1")
    private String otherDocument1;

    @JsonProperty("SIGN_DATA")
    private String signData;
}
