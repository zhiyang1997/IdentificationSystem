package com.engineer.recognitionSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Step3ReqDto {
    
    @JsonProperty("STORE_NAME")
    private String STORE_NAME;

    @JsonProperty("PRODUCT_NAME")
    private String PRODUCT_NAME;

    @JsonProperty("PRODUCT_PRICE")
    private String PRODUCT_PRICE;

    @JsonProperty("INSTALLMENT_MONTHS")
    private String INSTALLMENT_MONTHS;
    
    @JsonProperty("INSTALLMENT_AMOUNT")
    private String INSTALLMENT_AMOUNT;
    
    @JsonProperty("EMAIL")
    private String EMAIL;

    @JsonProperty("AVAILABLE_TIME")
    private String AVAILABLE_TIME;
}
