package com.example.charging.service;

import com.example.charging.payload.request.RateRequest;
import com.example.charging.payload.response.RateResponse;

public interface RateService {

    RateResponse calculateRate(RateRequest rateRequest) throws Exception;
}
