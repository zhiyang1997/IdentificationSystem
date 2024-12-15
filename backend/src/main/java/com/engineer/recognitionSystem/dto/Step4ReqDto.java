package com.engineer.recognitionSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Step4ReqDto {
    
    @JsonProperty("ID_CARD_FRONT")
    private String ID_CARD_FRONT;

    @JsonProperty("ID_CARD_BACK")
    private String ID_CARD_BACK;

    @JsonProperty("HEALTH_INSURANCE_CARD")
    private String HEALTH_INSURANCE_CARD;

    @JsonProperty("SELFIE_WITH_ID_CARD")
    private String SELFIE_WITH_ID_CARD;
    
    @JsonProperty("FINANCIAL_PROOF")
    private String FINANCIAL_PROOF;
    
    @JsonProperty("OTHER_DOCUMENT1")
    private String OTHER_DOCUMENT1;

    @JsonProperty("SIGN_DATA")
    private String SIGN_DATA;
}
