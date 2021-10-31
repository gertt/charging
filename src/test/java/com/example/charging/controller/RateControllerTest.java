package com.example.charging.controller;

import com.example.charging.payload.request.Rate;
import com.example.charging.payload.response.CalculatedData;
import com.example.charging.service.RateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.charging.util.AppConstants.RATE_ENERGY_OUTPUT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.charging.payload.request.Cdr;
import com.example.charging.payload.request.RateRequest;
import com.example.charging.payload.response.Components;
import com.example.charging.payload.response.RateResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RateController.class)
class RateControllerTest {

    @Autowired
    RateController rateController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RateService rateService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(rateController).build();
    }

    @Test
    void calculateRateStatusOK() throws Exception {

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
        RateResponse rateResponse = new RateResponse(true, RATE_ENERGY_OUTPUT, null, data);

        // when
        Mockito.when(rateService.calculateRate(any(RateRequest.class))).thenReturn(rateResponse);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{ \"data\": {\"overall\": 7.044, \"components\": {\"energy\": 3.277, \"time\": 2.767, \"transaction\": 1}}}"))
                .andReturn();
    }

    @Test
    void whenRateNullValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204307);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);
        rateRequest.setRate(null);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCdrHasNullValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.3);
        rate.setTime(2);
        rate.setTransaction(1);
        rateRequest.setRate(rate);
        rateRequest.setCdr(null);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRateEnergyHasNullValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(null);
        rate.setTime(2);
        rate.setTransaction(1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204307);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRateEnergyHasNegativeValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(-1.0);
        rate.setTime(2);
        rate.setTransaction(1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204307);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRateTimeHasNullValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.3);
        rate.setTime(null);
        rate.setTransaction(1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204307);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        //then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRateTimeHasNegativeValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.3);
        rate.setTime(-2);
        rate.setTransaction(1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204307);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRateTransactionHasNullValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.3);
        rate.setTime(2);
        rate.setTransaction(null);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204307);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRateTransactionHasNegativeValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.3);
        rate.setTime(2);
        rate.setTransaction(-1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204307);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCdrMeterStartHasNullValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.3);
        rate.setTime(2);
        rate.setTransaction(1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(null);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCdrMeterStartHasNegativeValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.3);
        rate.setTime(2);
        rate.setTransaction(-1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(-1204307);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCdrTimestampStartHasNullValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.3);
        rate.setTime(2);
        rate.setTransaction(1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204307);
        cdr.setTimestampStart(null);
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCdrTimestampStopHasNullValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.3);
        rate.setTime(2);
        rate.setTransaction(-1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204307);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(1215230);
        cdr.setTimestampStop(null);
        rateRequest.setCdr(cdr);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCdrMeterStopHasNullValue_thenReturns400() throws Exception {

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
        cdr.setMeterStop(null);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCdrMeterStopHasNegativeValue_thenReturns400() throws Exception {

        // given
        RateRequest rateRequest = new RateRequest();
        Rate rate = new Rate();
        rate.setEnergy(0.3);
        rate.setTime(2);
        rate.setTransaction(-1);
        rateRequest.setRate(rate);
        Cdr cdr = new Cdr();
        cdr.setMeterStart(1204307);
        cdr.setTimestampStart("2021-04-05T10:04:00Z");
        cdr.setMeterStop(-1215230);
        cdr.setTimestampStop("2021-04-05T11:27:00Z");
        rateRequest.setCdr(cdr);

        // then
        mockMvc.perform(post("/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateRequest)))
                .andExpect(status().isBadRequest());
    }
}