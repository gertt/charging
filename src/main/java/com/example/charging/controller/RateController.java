package com.example.charging.controller;

import com.example.charging.entity.City;
import com.example.charging.payload.request.RateRequest;
import com.example.charging.payload.response.RateResponse;
import com.example.charging.repository.CityRepository;
import com.example.charging.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

import static com.example.charging.util.AppConstants.*;

@RestController
public class RateController {

    public final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RateService rateService;

    @Autowired
    CityRepository cityRepository;

    /**
     * In this method is sent as input a json request and in output
     * returns a json response.Is called the service calculateRate
     * which calculates the rate to a corresponding CDR and returns a RateResponse object.
     **/
    @PostMapping("/rate")
    ResponseEntity<?> calculateRate(@Valid @RequestBody RateRequest rateRequest, WebRequest request) throws Exception {
        log.info(START_SERVICE + ((ServletWebRequest) request).getRequest().getRequestURI() + ": {}", rateRequest);
        RateResponse response = rateService.calculateRate(rateRequest);
        log.info(STOP_SERVICE + ((ServletWebRequest) request).getRequest().getRequestURI() + ": {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/sett/{cityName}")
    ResponseEntity<?> sett(@PathVariable String cityName) {
        City city = new  City();
        city.setCityName(cityName);
        cityRepository.save(city);
        return ResponseEntity.status(HttpStatus.OK).body(cityRepository.findAll());
    }

    @GetMapping("/gett/{number}")
    ResponseEntity<?> calculateRateGet(@PathVariable Long number) {
        return ResponseEntity.status(HttpStatus.OK).body(cityRepository.findById(number));
    }
}
