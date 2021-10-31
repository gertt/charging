package com.example.charging.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class RateResponse {

    final boolean success;
    final String message;
    final List<String> fieldErrors;
    final CalculatedData data;
}
