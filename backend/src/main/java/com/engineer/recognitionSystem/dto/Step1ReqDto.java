package com.engineer.recognitionSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Step1ReqDto {

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("PHONE_NUMBER")
    private String phoneNumber;

    @JsonProperty("LANDLINE_PHONE")
    private String landlinePhone;

    @JsonProperty("NATIONAL_ID")
    private String nationalId;

    @JsonProperty("BIRTHDATE")
    private String birthdate;

    @JsonProperty("REGISTERED_ADDRESS")
    private String registeredAddress;

    @JsonProperty("COMMUNICATION_ADDRESS")
    private String communicationAddress;

    @JsonProperty("RELATIVE_NAME1")
    private String relativeName1;

    @JsonProperty("RELATIVE_RELATION1")
    private String relativeRelation1;

    @JsonProperty("RELATIVE_PHONE1")
    private String relativePhone1;

    @JsonProperty("RELATIVE_NAME2")
    private String relativeName2;

    @JsonProperty("RELATIVE_RELATION2")
    private String relativeRelation2;

    @JsonProperty("RELATIVE_PHONE2")
    private String relativePhone2;

    @JsonProperty("FRIEND_NAME")
    private String friendName;

    @JsonProperty("FRIEND_PHONE")
    private String friendPhone;
}
