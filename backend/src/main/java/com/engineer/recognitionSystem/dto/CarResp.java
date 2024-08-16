package com.engineer.recognitionSystem.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CarResp {

    @JsonProperty("Manufacturer")
    private String manufacturer;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Min_Price")
    private BigDecimal minPrice;

    @JsonProperty("Price")
    private BigDecimal price;
}
