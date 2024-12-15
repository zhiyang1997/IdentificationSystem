package com.engineer.recognitionSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Step3ReqDto {

    @JsonProperty("STORE_NAME")
    private String storeName;

    @JsonProperty("PRODUCT_NAME")
    private String productName;

    @JsonProperty("PRODUCT_PRICE")
    private String productPrice;

    @JsonProperty("INSTALLMENT_MONTHS")
    private String installmentMonths;

    @JsonProperty("INSTALLMENT_AMOUNT")
    private String installmentAmount;

    @JsonProperty("EMAIL")
    private String email;

    @JsonProperty("AVAILABLE_TIME")
    private String availableTime;
}
