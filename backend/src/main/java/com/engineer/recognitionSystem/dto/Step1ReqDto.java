package com.engineer.recognitionSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Step1ReqDto {
    
    @JsonProperty("NAME")
    private String NAME;

    @JsonProperty("PHONE_NUMBER")
    private String PHONE_NUMBER;

    @JsonProperty("NATIONAL_ID")
    private String NATIONAL_ID;

    @JsonProperty("BIRTHDATE")
    private String BIRTHDATE;
    
    @JsonProperty("REGISTERED_ADDRESS")
    private String REGISTERED_ADDRESS;
    
    @JsonProperty("RELATIVE_NAME1")
    private String RELATIVE_NAME1;

    @JsonProperty("RELATIVE_RELATION1")
    private String RELATIVE_RELATION1;

    @JsonProperty("RELATIVE_PHONE1")
    private String RELATIVE_PHONE1;

    @JsonProperty("RELATIVE_NAME2")
    private String RELATIVE_NAME2;

    @JsonProperty("RELATIVE_RELATION2")
    private String RELATIVE_RELATION2;

    @JsonProperty("RELATIVE_PHONE2")
    private String RELATIVE_PHONE2;

    @JsonProperty("FRIEND_NAME")
    private String FRIEND_NAME;

    @JsonProperty("FRIEND_PHONE")
    private String FRIEND_PHONE;
}
