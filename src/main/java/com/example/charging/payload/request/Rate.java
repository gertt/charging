package com.example.charging.payload.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class Rate {

    @NotNull(message = "energy field is mandatory")
    @Positive(message = "energy must be greater than 0")
    private Double energy;

    @NotNull(message = "time field is mandatory")
    @Positive(message = "time must be greater than 0")
    private Integer time;

    @NotNull(message = "transaction field is mandatory")
    @Positive(message = "transaction must be greater than 0")
    private Integer transaction;
}
