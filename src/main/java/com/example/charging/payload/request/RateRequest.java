package com.example.charging.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RateRequest {

    @NotNull(message = "Rate is mandatory")
    @Valid
    private Rate rate;

    @NotNull(message = "Cdr is mandatory")
    @Valid
    private Cdr cdr;
}


