package com.engineer.recognitionSystem.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CarReq {

    @NotBlank
    @JsonProperty("Manufacturer")
    private String manufacturer;

    @NotBlank
    @JsonProperty("Type")
    private String type;

    @JsonProperty("Min_Price")
    private BigDecimal minPrice;

    @JsonProperty("Price")
    private BigDecimal price;
}
