package com.engineer.recognitionSystem.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Response<T> {
    @JsonProperty("Message")
    private String message;

    @JsonProperty("Datas")
    private List<T> datas;
}
