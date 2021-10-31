package com.example.charging.service;

import com.example.charging.payload.request.Cdr;
import com.example.charging.payload.request.Rate;
import com.example.charging.payload.request.RateRequest;
import com.example.charging.payload.response.CalculatedData;
import com.example.charging.payload.response.Components;
import com.example.charging.payload.response.RateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static com.example.charging.util.AppConstants.RATE_ENERGY_OUTPUT;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = RateServiceImpl.class)
class RateServiceTest {

    @Autowired
    private RateService rateService;

    @Test
    void calculateRateFirstCase() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.3);
        rate.setTime(2);
        rate.setTransaction(1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204307);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        CalculatedData data = new CalculatedData();
        data.setOverall(7.044);
        Components components = new Components();
        components.setEnergy(3.277);
        components.setTime(2.767);
        components.setTransaction(1);
        data.setComponents(components);
        RateResponse rateResponse = new RateResponse(true, "Rate output !", null, data);

        // when
        RateResponse response = rateService.calculateRate(rateRequest);

        // then
        assertEquals(rateResponse.isSuccess(), response.isSuccess());
        assertEquals(rateResponse.getMessage(), response.getMessage());
        assertEquals(rateResponse.getData(), response.getData());
    }

    @Test
    void calculateRateFirstSecondCase() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.4);
        rate.setTime(3);
        rate.setTransaction(1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204107);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215530);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        CalculatedData data = new CalculatedData();
        data.setOverall(9.719);
        Components components = new Components();
        components.setEnergy(4.569);
        components.setTime(4.15);
        components.setTransaction(1);
        data.setComponents(components);
        RateResponse rateResponse = new RateResponse(true, RATE_ENERGY_OUTPUT, null, data);

        // when
        RateResponse response = rateService.calculateRate(rateRequest);

        // then
        assertEquals(rateResponse.isSuccess(), response.isSuccess());
        assertEquals(rateResponse.getMessage(), response.getMessage());
        assertEquals(rateResponse.getData(), response.getData());
    }
}