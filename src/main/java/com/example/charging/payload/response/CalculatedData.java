package com.example.charging.payload.response;

import lombok.Data;

@Data
public class CalculatedData {

    private Double overall;

    private Components components;
}
