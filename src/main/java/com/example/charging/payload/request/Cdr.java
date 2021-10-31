package com.example.charging.payload.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class Cdr {

    @NotNull(message = "meterStart field is mandatory")
    @Positive(message = "meterStart must be greater than 0")
    private Integer meterStart;

    @NotEmpty(message = "timestampStart field is mandatory")
    private String timestampStart;

    @NotNull(message = "meterStop field is mandatory")
    @Positive(message = "meterStop must be greater than 0")
    private Integer meterStop;

    @NotEmpty(message = "timestampStop field is mandatory")
    private String timestampStop;
}

